package com.tournesol.bean;

public class DistanceRendezVousBean {

    private String sourceEventId;

    private String targetEventId;

    private String distance;

    private String duration;

    public DistanceRendezVousBean(String sourceEventId, String targetEventId, String distance, String duration) {
        this.sourceEventId = sourceEventId;
        this.targetEventId = targetEventId;
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

    public String getSourceEventId() {
        return sourceEventId;
    }

    public void setSourceEventId(String sourceEventId) {
        this.sourceEventId = sourceEventId;
    }

    public String getTargetEventId() {
        return targetEventId;
    }

    public void setTargetEventId(String targetEventId) {
        this.targetEventId = targetEventId;
    }
}
