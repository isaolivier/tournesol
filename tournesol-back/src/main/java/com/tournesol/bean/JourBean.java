package com.tournesol.bean;

import com.google.common.collect.Lists;
import com.tournesol.bean.output.EventOutputBean;

import java.time.LocalDate;
import java.util.List;

/**
 * Propostions de rendez-vous pour une date donnée.
 * Les events sont les évènements déjà programmés lors de cette journée.
 */
public class JourBean {

    private LocalDate date;

    private List<EventOutputBean> events;

    public JourBean(LocalDate date, EventOutputBean event) {
        this.date = date;
        this.events = Lists.newArrayList(event);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<EventOutputBean> getEvents() {
        return events;
    }

    public void setEvents(List<EventOutputBean> events) {
        this.events = events;
    }
}
