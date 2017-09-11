package com.tournesol.service.events;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.tournesol.bean.AuthInfo;
import com.tournesol.mapper.EventGoogleMapper;
import com.tournesol.service.auth.AuthService;
import com.tournesol.service.entity.EventEntity;

import java.io.IOException;
import java.time.ZonedDateTime;
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
     * @param authInfo
     * @param day
     * @return
     */
    public List<EventEntity> getEvents(AuthInfo authInfo, ZonedDateTime day) {
        final List<EventEntity> events = new ArrayList<>();
        Credential creds = authService.getCreds(authInfo);

        if (creds != null) {
            Calendar calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), creds).setApplicationName("GCal FTW MOFO")
                    .build();
            try {
                final Events googleEvents = calendar.events().list("primary").execute();

                final List<Event> googleEventList = googleEvents.getItems();
                final List<EventEntity> googlEvents = googleEventList.stream()
                        .map(e -> EventGoogleMapper.INSTANCE.googleEventToEventEntity(e))
                        .collect(Collectors.toList());
                events.addAll(googlEvents);

            } catch (IOException e) {
                LOGGER.error("Could fetch calendars", e);
            }
        } else {
            LOGGER.error("Could fetch calendars as user is not logged in");
        }
        return events;

//        List<EventEntity> rdvs = new ArrayList<>();
//
//        EventEntity event1 = new EventEntity();
//        event1.setiCalUID("rdv1");
//        event1.setDescription("Entretien Poêle à Granulés / Mr Durand");
//        event1.setStart(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 9, 0, 0, 0, day.getZone()));
//        event1.setEnd(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 10, 30, 0, 0, day.getZone()));
//
//        EventEntity event2 = new EventEntity();
//        event2.setiCalUID("rdv2");
//        event2.setDescription("Entretien Chaudière Fuel / Mr Dupond");
//        event2.setStart(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 11, 0, 0, 0, day.getZone()));
//        event2.setEnd(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 12, 30, 0, 0, day.getZone()));
//
//        rdvs.add(event1);
//        rdvs.add(event2);
//
//        return rdvs;
    }

}
