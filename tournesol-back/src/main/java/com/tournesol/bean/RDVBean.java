package com.tournesol.bean;

import java.time.ZonedDateTime;

/**
 * Created by redeyed on 7/23/17.
 */
public class RDVBean {
    private ZonedDateTime debut;
    private ZonedDateTime fin;

    public RDVBean() {
    }

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
