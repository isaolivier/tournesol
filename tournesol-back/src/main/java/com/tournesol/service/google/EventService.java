package com.tournesol.service.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.tournesol.bean.AuthInfo;
import com.tournesol.mapper.DateMapper;
import com.tournesol.mapper.EventMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de lecture/recherche/enregistrement/modification des évènements dans le calendrier client.
 */
@Service
public class EventService {

    @Autowired
    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    /**
     * Recherche des évènements correspondant au jour spécifié.
     */
    public List<Event> getEvents(AuthInfo authInfo, LocalDate day) {

        return getEvents(authInfo, day, 1);
    }

    /**
     * Recherche des évènements correspondant à partir du jour spécifié, et sur une plage de jours donnée.
     */
    public List<Event> getEvents(AuthInfo authInfo, LocalDate day, int dayRange) {

        final List<Event> events = new ArrayList<>();

        Credential creds = authService.getCreds(authInfo);

        if (creds != null) {
            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds).setApplicationName("TOURNESOL")
                    .build();
            try {
                final DateTime timeMin = DateMapper.map(day);
                final DateTime timeMax = DateMapper.map(day.plusDays(dayRange));

                final Events googleEvents = calendar.events().list("primary")
                        .setTimeMin(timeMin)
                        .setTimeMax(timeMax)
                        .execute();

                events.addAll(googleEvents.getItems());

            } catch (IOException e) {
                LOGGER.error("Could not fetch calendars", e);
            }
        } else {
            LOGGER.error("Could not fetch calendars as user is not logged in");
        }

        Collections.sort(events, Comparator.comparing(e -> EventMapper.INSTANCE.eventDateTimeToLocalDateTime(e.getStart())));

        return events;
    }

    /**
     * Création d'un évènement dans le calendrier google par défaut de l'utilisateur.
     */
    public Event createEvent(AuthInfo authInfo, Event event) {

        Credential creds = authService.getCreds(authInfo);

        Event result = null;

        if (creds != null) {

            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds)
                    .setApplicationName("TOURNESOL")
                    .build();

            try {

                LOGGER.info("Rdv créé " + event);
                result = calendar.events().insert("primary", event).execute();

            } catch (IOException e) {
                LOGGER.error("Could not create event", e);
            }
        }

        return result;
    }

}
