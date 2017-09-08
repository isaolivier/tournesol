/**
 * 
 */
package com.tournesol.service.auth;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private static final Logger       LOGGER             = LoggerFactory.getLogger(AuthService.class);

    /* Google API component configuration values */
    private static final List<String> SCOPES             = Arrays.asList("email", "profile", CalendarScopes.CALENDAR);
    private static final String       ACCESS_TYPE        = "offline";
    private static final String       APPROVAL_PROMPT    = "force";

    /* UID generation configuration values */
    private static final String       SALT               = "add-some-salt-over-it#";
    private static final String       ALGORITHM          = "SHA-256";
    private static final String       CLIENT_SECRET_FILE = "client_secret.json";
    static GoogleClientSecrets        SECRETS            = null;
    static AuthorizationCodeFlow      FLOW               = null;
    static MemoryDataStoreFactory     DATASTORE          = null;

    public AuthService() {
        initFlow();
    }

    private void initFlow() {
        getFlow();
    }

    public AuthInfo generateUID(AuthInfo authInfo) {
        if (authInfo != null && authInfo.getUID() == null) {
            try {
                MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
                byte[] hash = digest.digest((SALT + authInfo.getEmail()).getBytes(StandardCharsets.UTF_8));
                StringBuilder strBui = new StringBuilder();
                for (int i = 0; i < hash.length; i++) {
                    strBui.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
                authInfo.setUID(strBui.toString());
            } catch (Exception e) {
                LOGGER.error(String.format("Could not crypt user email '%s' using '%s'", authInfo.getEmail(), ALGORITHM), e);
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
                        clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret(), SCOPES).setDataStoreFactory(getDataStore())
                                .setAccessType(ACCESS_TYPE).setApprovalPrompt(APPROVAL_PROMPT).build();
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
            if (!StringUtils.isEmpty(authInfo.getCode())) {
                // Initial auth
                LOGGER.debug("###########################################################################################");
                LOGGER.debug(String.format("auth code detected, proceeding with OAuth2 workflow"));
                LOGGER.debug(String.format("Exchanging authcode for access token"));
                TokenResponse tokenResponse = null;
                try {
                    tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                            GoogleOAuthConstants.TOKEN_SERVER_URL, getGoogleClientSecrets().getDetails().getClientId(),
                            getGoogleClientSecrets().getDetails().getClientSecret(), authInfo.getCode(), "postmessage").execute();
                } catch (IOException e) {
                    LOGGER.error("Could not exchange auth code for access token", e);
                }
                try {
                    LOGGER.debug(String.format("Creating credentials from access token and storing it under id '%s'", authInfo.getUID()));
                    loggedUser = getFlow().createAndStoreCredential(tokenResponse, authInfo.getUID());
                    return loggedUser;
                } catch (IOException e) {
                    LOGGER.error(String.format("Could create and store credentials for", authInfo.getEmail()), e);
                }
                LOGGER.debug("###########################################################################################");
            } else {
                try {
                    loggedUser = getFlow().loadCredential(authInfo.getUID());
                } catch (IOException e) {
                    LOGGER.error("An error occured while retrieving credentials", e);
                }
                if (loggedUser != null) {
                    LOGGER.debug("###########################################################################################");
                    LOGGER.debug(String.format("%s is already logged into the app, restoring previously stored credentials", authInfo.getEmail()));
                    LOGGER.debug("###########################################################################################");
                    return loggedUser;
                }
            }
        }
        return null;
    }

    public boolean isSignedIn(String email) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.setEmail(email);
        Credential creds = getCreds(authInfo);
        boolean credsStillValid = creds != null && creds.getClock().currentTimeMillis() < creds.getExpirationTimeMilliseconds();
        if (creds != null) {
            LOGGER.debug(String.format("Current time: %s - Expiration time: %s", creds.getClock().currentTimeMillis(), creds.getExpirationTimeMilliseconds()));
            LOGGER.debug(String.format("Current time > Expiration time: %s", creds.getClock().currentTimeMillis() > creds.getExpirationTimeMilliseconds()));
        }
        return credsStillValid;
    }

}