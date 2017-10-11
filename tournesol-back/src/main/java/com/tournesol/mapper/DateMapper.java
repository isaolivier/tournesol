package com.tournesol.mapper;

import com.google.api.client.util.DateTime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;

@Mapper
public class DateMapper {

    public static LocalDateTime map(DateTime value) {
        if(value != null) {
            final ZoneOffset zoneOffsetInHours = ZoneOffset.ofHours(value.getTimeZoneShift() / 60);
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(value.getValue()), ZoneId.from(zoneOffsetInHours));
        }
        return null;
    };

    public static DateTime map(LocalDateTime value) {
        if(value != null) {
            final ZonedDateTime zdt = ZonedDateTime.of(value, ZoneId.systemDefault());
            return new DateTime(zdt.format(DateTimeFormatter.ISO_INSTANT));
        }
        return null;
    };

    public static DateTime map(final LocalDate day) {
        final ZonedDateTime zdt = ZonedDateTime.of(day, LocalTime.MIN, ZoneId.systemDefault());
        return new DateTime(zdt.format(DateTimeFormatter.ISO_INSTANT));
    }

    public static LocalDate mapDayToLacalDate(final DateTime day) {
        return LocalDate.ofEpochDay(day.getValue()/86400000);
    }
}
