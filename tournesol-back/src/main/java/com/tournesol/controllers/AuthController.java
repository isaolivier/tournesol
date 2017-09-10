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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        LOGGER.debug("Generating UID");
        String UID = authSvc.generateUID(authInfo);
        Credential creds = null;
        if (!StringUtils.isEmpty(authInfo.getUID()) && !UID.equals(authInfo.getUID())) {
    		LOGGER.error("UID mismatch - possible break in attempt");
        } else {
        	authInfo.setUID(UID);
        	LOGGER.debug(authInfo.toString());
            LOGGER.debug("Acquiring credentials");
            creds = authSvc.getCreds(authInfo);
        }
        LOGGER.debug(String.format("creds %s", creds));
        authInfo.setAuthcode(null);
        return new ResponseEntity<>(authInfo, creds != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin
    @PostMapping("/isSessionAlive")
    public ResponseEntity<Boolean> isSessionAlive(@RequestBody AuthInfo authInfo) {
        LOGGER.debug("#### isSessionAlive #####");
        boolean isSessionAlive = authSvc.isSessionAlive(authInfo);
        ResponseEntity<Boolean> resp = new ResponseEntity<>(isSessionAlive, HttpStatus.OK);
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