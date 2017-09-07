/**
 * 
 */
package com.tournesol.service.auth;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.tournesol.bean.AuthInfo;

/**
 * @author gtouati
 *
 */
@Service
public class AuthService {

    private static final Logger   LOGGER             = LoggerFactory.getLogger(AuthService.class);

    private static final String   SPEC               = "AES";
    private static final String   PASSPHRASE         = "cryptingKey82195";                        // 128 bit key
    private static final String   CLIENT_SECRET_FILE = "client_secret.json";
    static GoogleClientSecrets    SECRETS            = null;
    static AuthorizationCodeFlow  FLOW               = null;
    static MemoryDataStoreFactory DATASTORE          = null;

    public AuthService() {
        initFlow();
    }

    private void initFlow() {
        getFlow();
    }

    AuthInfo generateUID(AuthInfo authInfo) {
        if (authInfo != null && authInfo.getUID() == null) {
            try {
                // Create key and cipher
                SecretKeySpec aesKey = new SecretKeySpec(PASSPHRASE.getBytes(), SPEC);
                Cipher cipher = Cipher.getInstance(SPEC);
                // encrypt the text
                cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                byte[] encrypted = cipher.doFinal(authInfo.getEmail().getBytes());
                authInfo.setUID(new String(encrypted));
                // decrypt the text
                // cipher.init(Cipher.DECRYPT_MODE, aesKey);
                // String decrypted = new String(cipher.doFinal(encrypted));
                // System.err.println(decrypted);
            } catch (Exception e) {
                LOGGER.error(String.format("Could not crypt user email '%s' using '%s'", authInfo.getEmail(), SPEC), e);
            }
        }
        return authInfo;
    }

    GoogleClientSecrets getGoogleClientSecrets() {
        if (SECRETS == null) {
            try {
                SECRETS = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
                        new FileReader(getClass().getClassLoader().getResource(CLIENT_SECRET_FILE).getFile()));
            } catch (IOException e) {
                LOGGER.error("Could not load app secret", e);
                SECRETS = null;
            }
        }
        return SECRETS;
    }

    MemoryDataStoreFactory getDataStore() {
        if (DATASTORE == null) {
            DATASTORE = MemoryDataStoreFactory.getDefaultInstance();
        }
        return DATASTORE;
    }

    AuthorizationCodeFlow getFlow() {
        if (FLOW == null) {
            GoogleClientSecrets clientSecrets = getGoogleClientSecrets();
            try {
                FLOW = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                        clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret(),
                        Arrays.asList("email", "profile", CalendarScopes.CALENDAR)).setDataStoreFactory(getDataStore()).setAccessType("offline")
                                .setApprovalPrompt("force").build();
            } catch (IOException e) {
                LOGGER.error("Could not initialize app OAuth2 workflow", e);
                FLOW = null;
            }
        }
        return FLOW;
    }

    public Credential getCreds(AuthInfo authInfo) {
        if (getFlow() != null) {
            Credential loggedUser = null;
            AuthInfo authInfoWithUID = generateUID(authInfo);
            if (authInfo.getCode() != null) {
                // Initial auth
                LOGGER.debug("###########################################################################################");
                LOGGER.debug(String.format("auth code detected, proceeding with OAuth2 workflow"));
                LOGGER.debug(String.format("Exchanging authcode for access token"));
                TokenResponse tokenResponse = null;
                try {
                    tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                            GoogleOAuthConstants.TOKEN_SERVER_URL, getGoogleClientSecrets().getDetails().getClientId(),
                            getGoogleClientSecrets().getDetails().getClientSecret(), authInfoWithUID.getCode(), "postmessage").execute();
                } catch (IOException e) {
                    LOGGER.error("Could not exchange auth code for access token", e);
                }
                if (validateTokenResponse(tokenResponse)) {
                    try {
                        LOGGER.debug(String.format("Creating credentials from access token and storing it under id '%s'", authInfoWithUID.getUID()));
                        loggedUser = getFlow().createAndStoreCredential(tokenResponse, authInfoWithUID.getUID());
                        // If at some point we need more profile info
                        //                        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), loggedUser).setApplicationName("Oauth2").build();
                        //                        Userinfoplus userinfo = oauth2.userinfo().get().execute();
                        //                        LOGGER.debug(String.format("Userinfoplus: %s", userinfo.toPrettyString()));
                        return loggedUser;
                    } catch (IOException e) {
                        LOGGER.error(String.format("Could create and store credentials for", authInfoWithUID.getEmail()), e);
                    }
                } else {
                    LOGGER.error(String.format("Workflow interrupted - access token is not valid"));
                }
                LOGGER.debug("###########################################################################################");
            } else {
                try {
                    loggedUser = getFlow().loadCredential(authInfoWithUID.getUID());
                } catch (IOException e) {
                    LOGGER.error("An error occured while retrieving credentials", e);
                }
                if (loggedUser != null) {
                    LOGGER.debug("###########################################################################################");
                    LOGGER.debug(String.format("%s is already logged into the app, restoring previously stored credentials", authInfoWithUID.getEmail()));
                    LOGGER.debug("###########################################################################################");
                    return loggedUser;
                }
            }
        }
        return null;
    }

    boolean validateTokenResponse(TokenResponse token) {
        if (token != null) {
            if (token.getAccessToken() != null) {
                if (token.getRefreshToken() != null) {
                    LOGGER.debug("There is a refresh token and an access token (useable)");
                } else {
                    LOGGER.debug("There is no refresh token - access token only (still useable)");
                }
                return true;
            }
        }
        return false;
    }

    public void logCreds(Credential creds) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Creds: %s", creds)).append(System.lineSeparator());
        if (creds != null) {
            builder.append(String.format("getClock().currentTimeMillis : %s", creds.getClock().currentTimeMillis())).append(System.lineSeparator());
            builder.append(String.format("getExpirationTimeMilliseconds: %s", creds.getExpirationTimeMilliseconds())).append(System.lineSeparator());
            builder.append(String.format("getExpiresInSeconds: %s", creds.getExpiresInSeconds())).append(System.lineSeparator());
            builder.append(String.format("getMethod: %s", creds.getMethod())).append(System.lineSeparator());
            builder.append(String.format("getTokenServerEncodedUrl: %s", creds.getTokenServerEncodedUrl())).append(System.lineSeparator());
            builder.append(String.format("getAccessToken: %s", creds.getAccessToken()));
        }
        LOGGER.debug(builder.toString());
    }

}