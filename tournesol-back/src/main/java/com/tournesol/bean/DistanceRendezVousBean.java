package com.tournesol.bean;

import java.time.LocalDateTime;

public class DistanceRendezVousBean implements SegmentBean {

    private LocalDateTime start;

    private LocalDateTime end;

    private String distance;

    private String duration;

    public DistanceRendezVousBean(LocalDateTime start, LocalDateTime end, String distance, String duration) {
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @Override
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
