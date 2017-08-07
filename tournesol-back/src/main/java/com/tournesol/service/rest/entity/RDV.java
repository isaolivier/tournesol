package com.tournesol.service.rest.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.time.ZonedDateTime;

/**
 * Created by redeyed on 7/23/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDV {

    private ZonedDateTime debut;
    private ZonedDateTime fin;

    public ZonedDateTime getDebut() {
        return debut;
    }

    public void setDebut(ZonedDateTime debut) {
        this.debut = debut;
    }

    public ZonedDateTime getFin() {
        return fin;
    }

    public void setFin(ZonedDateTime fin) {
        this.fin = fin;
    }
}
