package com.tournesol.mapper;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.tournesol.bean.input.EventInputBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iolivier on 23/09/2017.
 */
public abstract class EventMapperDecorator implements EventMapper {

    private final EventMapper delegate;

    public EventMapperDecorator(EventMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public Event eventInputBeanToEvent(EventInputBean eventBean) {

        Event event = delegate.eventInputBeanToEvent(eventBean);
        event.setStart(dateAndTimeToEventDateTime(eventBean.getDate(), eventBean.getStartTime()));
        event.setEnd(dateAndTimeToEventDateTime(eventBean.getDate(), eventBean.getEndTime()));

        Map<String, String> extendedPropertiesMap = new HashMap<>();
        extendedPropertiesMap.put("latitude", eventBean.getLatitude());
        extendedPropertiesMap.put("longitude", eventBean.getLongitude());

        final Event.ExtendedProperties extendedProperties = new Event.ExtendedProperties();
        extendedProperties.setShared(extendedPropertiesMap);
        event.setExtendedProperties(extendedProperties);

        return event;
    }

    private EventDateTime dateAndTimeToEventDateTime(LocalDate date, LocalTime time) {
        return zonedDateTimeToEventDateTime(ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris")));
    }
}
