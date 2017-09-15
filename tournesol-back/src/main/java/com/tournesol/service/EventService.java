package com.tournesol.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.tournesol.bean.AuthInfo;
import com.tournesol.mapper.DateMapper;
import com.tournesol.mapper.EventGoogleMapper;
import com.tournesol.service.entity.EventEntity;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service de lecture/recherche/enregistrement/modification des évènements dans le calendrier client.
 */
@Component
public class EventService {

    @Autowired
    AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    /**
     * Recherche des évènements correspondant au jour spécifié.
     */
    public List<EventEntity> getEvents(AuthInfo authInfo, ZonedDateTime day) {
        final List<EventEntity> events = new ArrayList<>();
        Credential creds = authService.getCreds(authInfo);

        if (creds != null) {
            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds).setApplicationName("TOURNESOL")
                    .build();
            try {
                final DateTime timeMin = DateMapper.map(day.truncatedTo(ChronoUnit.DAYS));
                final DateTime timeMax = DateMapper.map(day.plusDays(1).truncatedTo(ChronoUnit.DAYS));

                final Events googleEvents = calendar.events().list("primary")
                        .setTimeMin(timeMin)
                        .setTimeMax(timeMax)
                        .execute();

                final List<Event> googleEventList = googleEvents.getItems();
                final List<EventEntity> googlEvents = googleEventList.stream()
                        .map(e -> EventGoogleMapper.INSTANCE.googleEventToEventEntity(e))
                        .collect(Collectors.toList());
                events.addAll(googlEvents);

            } catch (IOException e) {
                LOGGER.error("Could fetch calendars", e);
            }
        } else {
            LOGGER.error("Could not fetch calendars as user is not logged in");
        }
        return events;

    }

    /**
     * Création d'un évènement dans le calendrier google par défaut de l'utilisateur.
     */
    public EventEntity createEvent(AuthInfo authInfo, EventEntity eventEntity) {

        Credential creds = authService.getCreds(authInfo);

        EventEntity result = null;

        if (creds != null) {

            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds).setApplicationName("TOURNESOL")
                    .build();

            try {

                Event googleEvent = EventGoogleMapper.INSTANCE.eventEntityToGoogleEvent(eventEntity);

                Event resultGoogleEvent = calendar.events().insert("primary", googleEvent).execute();

                result = EventGoogleMapper.INSTANCE.googleEventToEventEntity(resultGoogleEvent);

            } catch (IOException e) {
                LOGGER.error("Could not create event", e);
            }
        }

        return result;
    }

}
