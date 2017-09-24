package com.tournesol.bean.output;

import com.tournesol.bean.EventBean;

import java.time.ZonedDateTime;

/**
 * Created by iolivier on 22/09/2017.
 */
public class EventOutputBean extends EventBean {

    private ZonedDateTime start;

    private ZonedDateTime end;

    private String colorId;

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

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
