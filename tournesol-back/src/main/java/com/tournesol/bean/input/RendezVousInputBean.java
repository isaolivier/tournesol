package com.tournesol.bean.input;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousInputBean implements Serializable {

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private List<Long> appareils = new ArrayList<>();

    private Long client;

    public List<Long> getAppareils() {
        return appareils;
    }

    public void setAppareils(List<Long> appareils) {
        this.appareils = appareils;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

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
}
