package com.tournesol.bean.output;

import com.tournesol.bean.Coordonnees;
import com.tournesol.bean.EventBean;
import com.tournesol.bean.NextEventBean;

import java.time.ZonedDateTime;

/**
 * Created by iolivier on 22/09/2017.
 */
public class EventOutputBean extends EventBean {

    private ZonedDateTime start;

    private ZonedDateTime end;

    private Coordonnees coordonnees;

    private NextEventBean nextEvent;

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public NextEventBean getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(NextEventBean nextEvent) {
        this.nextEvent = nextEvent;
    }
}
