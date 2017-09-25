package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.tournesol.bean.input.EventInputBean;
import com.tournesol.bean.output.EventOutputBean;

import java.time.ZonedDateTime;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(EventMapperDecorator.class)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventInputBean eventToEventInputBean(Event event);

    Event eventInputBeanToEvent(EventInputBean eventBean);

    EventOutputBean eventToEventOutputBean(Event event);

    Event eventOutBeanToEvent(EventOutputBean eventBean);

    default EventDateTime zonedDateTimeToEventDateTime(ZonedDateTime value) {
        EventDateTime eventDateTime = new EventDateTime().setDateTime(DateMapper.map(value));
        return eventDateTime;
    }

    ;

    default ZonedDateTime eventDateTimeToZonedDateTime(EventDateTime value) {
        return DateMapper.map(value.getDateTime());
    }

    ;

}

