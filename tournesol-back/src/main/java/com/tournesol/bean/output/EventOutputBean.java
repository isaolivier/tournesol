package com.tournesol.bean.output;

import com.tournesol.bean.Coordonnees;
import com.tournesol.bean.EventBean;

import java.time.LocalDateTime;

/**
 * Created by iolivier on 22/09/2017.
 */
public class EventOutputBean extends EventBean {

    private LocalDateTime start;

    private LocalDateTime end;

    private Coordonnees coordonnees;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }
}
