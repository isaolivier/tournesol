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
import com.google.api.services.calendar.model.CalendarList;
import com.tournesol.bean.AuthInfo;
import com.tournesol.service.auth.AuthService;
import com.tournesol.service.calendars.GoogleCalendarService;

/**
 * @author gtouati
 *
 */
@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService                 authSvc;

    @Autowired
    GoogleCalendarService       calSvc;

    @CrossOrigin
    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody AuthInfo authInfo) throws FileNotFoundException, IOException {
        LOGGER.debug("#### auth #####");
        LOGGER.debug(authInfo.toString());
        Credential creds = authSvc.getCreds(authSvc.generateUID(authInfo));
        LOGGER.debug(String.format("AuthInfo %s", authInfo));
        return new ResponseEntity<>(authInfo, creds != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin
    @GetMapping("/isSignedIn")
    public ResponseEntity<Boolean> isSignedIn(@RequestParam String email) {
        LOGGER.debug("#### isSignedIn #####");
        boolean signedIn = authSvc.isSignedIn(email);
        ResponseEntity<Boolean> resp = new ResponseEntity<>(signedIn, HttpStatus.OK);
        return resp;
    }

    @CrossOrigin
    @PostMapping("/fetchCal")
    public ResponseEntity<?> fetchCal(@RequestBody AuthInfo authInfo) {
        LOGGER.debug("#### fetchCal #####");
        LOGGER.debug(authInfo.toString());
        Credential creds = authSvc.getCreds(authInfo);
        CalendarList calendars = calSvc.fetchCalendars(creds);
        return new ResponseEntity<>(calendars, calendars != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

}