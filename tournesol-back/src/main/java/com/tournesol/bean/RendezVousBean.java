package com.tournesol.bean;

import java.util.ArrayList;
import java.util.List;

public class RendezVousBean {

    private ClientBean client;

    private List<AppareilBean> appareils = new ArrayList<>();

    private EventBean event;

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

    public EventBean getEvent() {
        return event;
    }

    public void setEvent(EventBean event) {
        this.event = event;
    }
}
