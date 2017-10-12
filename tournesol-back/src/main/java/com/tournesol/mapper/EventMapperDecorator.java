package com.tournesol.mapper;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.tournesol.bean.Coordonnees;
import com.tournesol.bean.input.EventInputBean;
import com.tournesol.bean.output.EventOutputBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        return event;
    }

    @Override
    public EventOutputBean eventToEventOutputBean(Event event) {

        EventOutputBean eventOutputBean = delegate.eventToEventOutputBean(event);

        if(event.getExtendedProperties().getShared() != null && event.getExtendedProperties().getShared().containsKey("latitude") && event.getExtendedProperties().getShared().containsKey("longitude")) {
            eventOutputBean.setCoordonnees(new Coordonnees(
                    Double.valueOf(event.getExtendedProperties().getShared().get("latitude")),
                    Double.valueOf(event.getExtendedProperties().getShared().get("longitude"))));
        }

        return eventOutputBean;
    }

    private EventDateTime dateAndTimeToEventDateTime(LocalDate date, LocalTime time) {
        return localDateTimeToEventDateTime(LocalDateTime.of(date, time));
    }
}
