package com.tournesol.service.events;

import com.tournesol.service.entity.EventEntity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Service de lecture/recherche/enregistrement/modification des évènements dans le calendrier client.
 */
@Component
public class EventService {


    public List<EventEntity> getEvents(ZonedDateTime day) {

        List<EventEntity> rdvs = new ArrayList<>();

        EventEntity event1 = new EventEntity();
        event1.setiCalUID("rdv1");
        event1.setDescription("Entretien Poêle à Granulés / Mr Durand");
        event1.setStart(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 9, 0, 0, 0, day.getZone()));
        event1.setEnd(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 10, 30, 0, 0, day.getZone()));

        EventEntity event2 = new EventEntity();
        event2.setiCalUID("rdv2");
        event2.setDescription("Entretien Chaudière Fuel / Mr Dupond");
        event2.setStart(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 11, 0, 0, 0, day.getZone()));
        event2.setEnd(ZonedDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 12, 30, 0, 0, day.getZone()));

        rdvs.add(event1);
        rdvs.add(event2);

        return rdvs;
    }

}
