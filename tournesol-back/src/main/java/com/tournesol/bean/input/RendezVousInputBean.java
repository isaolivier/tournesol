package com.tournesol.bean.input;

import com.tournesol.bean.EventBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RendezVousInputBean implements Serializable {

    private EventBean event;

    private List<Long> appareils = new ArrayList<>();

    public List<Long> getAppareils() {
        return appareils;
    }

    public void setAppareils(List<Long> appareils) {
        this.appareils = appareils;
    }

    public EventBean getEvent() {
        return event;
    }

    public void setEvent(EventBean event) {
        this.event = event;
    }
}
