package com.tournesol.bean.input;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousInputBean implements Serializable {

    private List<Long> appareils = new ArrayList<>();

    private Long client;

    private EventInputBean event;

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


    public EventInputBean getEvent() {
        return event;
    }

    public void setEvent(EventInputBean event) {
        this.event = event;
    }
}
