package com.tournesol.controllers;

import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by draluy on 19/07/2017.
 */
@Slf4j // Lombok logger
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserController {

    @Autowired
    private HttpSession                      httpSession;

    private static final Map<String, String> TOKEN_STORE = new HashMap<>();

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @PostMapping("/authcode")
    public ResponseEntity<?> authcode(@RequestBody String authCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        debugSessionInfo();
        debugCookies(request);

        if (request.getHeader("X-Requested-With") == null) {
            // Without the `X-Requested-With` header, this request could be
            // forged. Aborts.
        }

        log.debug(String.format("Authcode is %s", authCode));
        // (Receive authCode via HTTPS POST)

        // Set path to the Web application client_secret_*.json file you
        // downloaded from the
        // Google API Console:
        // https://console.developers.google.com/apis/credentials
        // You can also find your Web application client ID and client secret
        // from the
        // console and specify them directly when you create the
        // GoogleAuthorizationCodeTokenRequest
        // object.
        String CLIENT_SECRET_FILE = "client_secret_wo_redirects.json";

        // Exchange auth code for access token
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
                new FileReader(getClass().getClassLoader().getResource(CLIENT_SECRET_FILE).getFile()));
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                "https://www.googleapis.com/oauth2/v4/token", clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret(), authCode, // authCode,
                "postmessage"//"http://localhost:9001/authcallback" 'urn:ietf:wg:oauth:2.0:oob' // REDIRECT_URI
        ) // Specify the same redirect URI that you use with your web
                // app. If you don't have a web version of your app, you can
                // specify an empty string.
                .execute();

        storeToken(tokenResponse);

        String accessToken = tokenResponse.getAccessToken();
        log.debug(String.format("AccessToken: %s", accessToken));

        // Use access token to call API
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName("Tournesol")
                .build();

        CalendarList calendarList = calendar.calendarList().list().execute();
        for (CalendarListEntry entry : calendarList.getItems()) {
            log.debug(String.format("Entry desc: %s - summary: %s", entry.getDescription(), entry.getSummary()));
        }

        // Get profile info from ID token
        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject(); // Use this value as a key to
                                              // identify a user.
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        log.debug(String.format("idToken: %s", idToken));
        log.debug(String.format("payload: %s", payload));
        log.debug(String.format("userId: %s", userId));
        log.debug(String.format("email: %s", email));
        log.debug(String.format("emailVerified: %s", emailVerified));
        log.debug(String.format("name: %s", name));
        log.debug(String.format("pictureUrl: %s", pictureUrl));
        log.debug(String.format("locale: %s", locale));
        log.debug(String.format("familyName: %s", familyName));
        log.debug(String.format("givenName: %s", givenName));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void storeToken(GoogleTokenResponse tokenResponse) {
        // A revoir
        try {
            TOKEN_STORE.put(tokenResponse.parseIdToken().getPayload().getSubject(), tokenResponse.getAccessToken());
        } catch (IOException e) {
            log.error(String.format("Could not store token for %s", tokenResponse));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest request, HttpServletResponse response) {
        debugSessionInfo();
        debugCookies(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void debugSessionInfo() {
        log.debug(String.format("jsessionid", RequestContextHolder.currentRequestAttributes().getSessionId()));
        log.debug(String.format("HttpSession.getCreationTime(): %s", httpSession.getCreationTime()));
        log.debug(String.format("HttpSession.getId(): %s", httpSession.getId()));
        log.debug(String.format("HttpSession.getLastAccessedTime(): %s", httpSession.getLastAccessedTime()));
        log.debug(String.format("HttpSession.getMaxInactiveInterval(): %s", httpSession.getMaxInactiveInterval()));
    }

    private void debugCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.debug(String.format("Cookie name: %s - value: %s", cookie.getName(), cookie.getValue()));
            }
        } else {
            log.debug("No cookies");
        }
    }

}
