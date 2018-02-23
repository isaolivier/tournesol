/**
 * 
 */
package com.tournesol.service.google;

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

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public String generateUID(AuthInfo authInfo) {
        if (authInfo != null && !StringUtils.isEmpty(authInfo.getEmail())) {
            try {
                MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
                byte[] hash = digest.digest((SALT + authInfo.getEmail()).getBytes(StandardCharsets.UTF_8));
                StringBuilder strBui = new StringBuilder();
                for (int i = 0; i < hash.length; i++) {
                    strBui.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
                }
                return strBui.toString();
            } catch (Exception e) {
                LOGGER.error(String.format("Could not encrypt user email '%s' using '%s' and some salt", authInfo.getEmail(), ALGORITHM), e);
            }
        }
        return null;
    }

    GoogleClientSecrets getGoogleClientSecrets() {
        if (SECRETS == null) {
            try {
                SECRETS = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
                        new InputStreamReader(getClass().getClassLoader().getResourceAsStream(CLIENT_SECRET_FILE)));
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
            if (!StringUtils.isEmpty(authInfo.getAuthcode())) {
                // Initial auth
                LOGGER.debug("###########################################################################################");
                LOGGER.debug(String.format("auth code detected, proceeding with OAuth2 workflow"));
                LOGGER.debug(String.format("Exchanging authcode for access token"));
                TokenResponse tokenResponse = null;
                try {
                    tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                            GoogleOAuthConstants.TOKEN_SERVER_URL, getGoogleClientSecrets().getDetails().getClientId(),
                            getGoogleClientSecrets().getDetails().getClientSecret(), authInfo.getAuthcode(), "postmessage").execute();
                } catch (com.google.api.client.auth.oauth2.TokenResponseException e) {
                    LOGGER.error(String.format("Could not exchange auth code for access token: %n%s", e.getContent()));
                } catch (IOException e) {
                	LOGGER.error("Could not exchange auth code for access token", e);
                }
                if (tokenResponse!=null) {
                	try {
                        LOGGER.debug(String.format("Creating credentials from access token and storing it under id '%s'", authInfo.getUID()));
                        LOGGER.debug(String.format("/!\\ REMOVE ME !!! TokenResponse: %n%s",tokenResponse.toPrettyString()));
                        return getFlow().createAndStoreCredential(tokenResponse, authInfo.getUID());
                    } catch (IOException e) {
                        LOGGER.error(String.format("Could create and store credentials for", authInfo.getEmail()), e);
                    }
                }
                LOGGER.debug("###########################################################################################");
            } else if (!StringUtils.isEmpty(authInfo.getUID())) {
            	// Already logged in user
            	String generated = this.generateUID(authInfo);
            	if (authInfo.getUID().equals(generated)) {
                    Credential loggedUser = null;
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
            	} else {
            		LOGGER.error("###########################################################################################");
                    LOGGER.error(String.format("Generated UID does not match provided UID %s"), authInfo.getUID());
                    LOGGER.error("###########################################################################################");
            	}
            }
        }
        return null;
    }

    public boolean isSessionAlive(AuthInfo authInfo) {
        Credential creds = getCreds(authInfo);
        boolean credsStillValid = creds != null && creds.getClock().currentTimeMillis() < creds.getExpirationTimeMilliseconds();
        if (creds != null) {
            LOGGER.debug(String.format("Current time: %s - Expiration time: %s", creds.getClock().currentTimeMillis(), creds.getExpirationTimeMilliseconds()));
            LOGGER.debug(String.format("Current time > Expiration time: %s", creds.getClock().currentTimeMillis() > creds.getExpirationTimeMilliseconds()));
        }
        return credsStillValid;
    }

}