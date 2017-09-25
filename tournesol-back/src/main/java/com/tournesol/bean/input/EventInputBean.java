package com.tournesol.bean.input;

import com.tournesol.bean.EventBean;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by iolivier on 22/09/2017.
 */
public class EventInputBean extends EventBean {

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String latitude;

    private String longitude;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
