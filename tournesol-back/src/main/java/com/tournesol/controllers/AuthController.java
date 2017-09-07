/**
 * 
 */
package com.tournesol.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.tournesol.bean.AuthInfo;
import com.tournesol.service.auth.AuthService;

/**
 * @author gtouati
 *
 */
@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService                 authSvc;

    @CrossOrigin
    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthInfo authInfo) throws FileNotFoundException, IOException {
        LOGGER.debug("#### auth #####");
        LOGGER.debug(authInfo.toString());
        Credential creds = authSvc.getCreds(authInfo);
        authSvc.logCreds(creds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/creds")
    public ResponseEntity<?> creds(@RequestParam String email) {
        LOGGER.debug("#### creds #####");
        AuthInfo authInfo = new AuthInfo();
        authInfo.setEmail(email);
        Credential creds = authSvc.getCreds(authInfo);
        authSvc.logCreds(creds);
        return new ResponseEntity<>(creds, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/fetchCal")
    public ResponseEntity<?> fetchCal(@RequestParam String email) {
        LOGGER.debug("#### fetchCal #####");
        LOGGER.debug(email);
        AuthInfo authInfo = new AuthInfo();
        authInfo.setEmail(email);
        Credential creds = authSvc.getCreds(authInfo);
        authSvc.logCreds(creds);
        if (creds != null) {
            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds).setApplicationName("GCal FTW MOFO")
                    .build();
            try {
                CalendarList calendarList = calendar.calendarList().list().execute();
                for (CalendarListEntry entry : calendarList.getItems()) {
                    LOGGER.debug(String.format("Entry desc: %s - summary: %s", entry.getDescription(), entry.getSummary()));
                }
            } catch (IOException e) {
                LOGGER.error("Could fetch calendars", e);
            }
        } else {
            LOGGER.error("Could fetch calendars as user is not logged in");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}