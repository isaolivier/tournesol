package com.tournesol.mapper;

import com.google.api.client.util.DateTime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;

@Mapper
public class DateMapper {
    public static ZonedDateTime map(DateTime value) {
        final ZoneOffset zoneOffsetInHours = ZoneOffset.ofHours(value.getTimeZoneShift() / 60);
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(value.getValue()), ZoneId.from(zoneOffsetInHours));
    };

    public static DateTime map(ZonedDateTime value) {
        return new DateTime(value.format(DateTimeFormatter.ISO_INSTANT));
    };
}
