package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.tournesol.service.entity.EventEntity;

import java.time.ZonedDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper (uses = DateMapper.class)
public interface EventGoogleMapper {

    EventGoogleMapper INSTANCE = Mappers.getMapper( EventGoogleMapper.class );

    EventEntity googleEventToEventEntity(Event googleEvent);
    Event eventEntityToGoogleEvent(EventEntity eventEntity);

    default ZonedDateTime map(EventDateTime value) {
        return DateMapper.map (value.getDateTime());
    };

    default EventDateTime map(ZonedDateTime value) {
        EventDateTime eventDateTime = new EventDateTime()
                .setDateTime(DateMapper.map (value));
        return eventDateTime;
    };
}

