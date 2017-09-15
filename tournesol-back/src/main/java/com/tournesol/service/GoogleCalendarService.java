/**
 * 
 */
package com.tournesol.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author gtouati
 *
 */
@Service
public class GoogleCalendarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCalendarService.class);

    public CalendarList fetchCalendars(Credential creds) {
        if (creds != null) {
            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds).setApplicationName("GCal FTW MOFO")
                    .build();
            try {
                return calendar.calendarList().list().execute();
            } catch (IOException e) {
                LOGGER.error("Could fetch calendars", e);
            }
        } else {
            LOGGER.error("Could fetch calendars as user is not logged in");
        }
        return null;
    }

}
