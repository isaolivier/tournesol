package com.tournesol.bean;

import com.tournesol.bean.output.EventOutputBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RendezVousBean implements Serializable {

    private ClientBean client;

    private List<AppareilBean> appareils = new ArrayList<>();

    private EventOutputBean event;

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }

    public List<AppareilBean> getAppareils() {
        return appareils;
    }

    public void setAppareils(List<AppareilBean> appareils) {
        this.appareils = appareils;
    }

    public EventOutputBean getEvent() {
        return event;
    }

    public void setEvent(EventOutputBean event) {
        this.event = event;
    }
}
