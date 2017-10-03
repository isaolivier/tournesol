package com.tournesol.bean;

public class NextEventBean {

    private String distance;

    private String duration;

    private String durationInTrafic;

    public NextEventBean(String distance, String duration, String durationInTrafic) {
        this.distance = distance;
        this.duration = duration;
        this.durationInTrafic = durationInTrafic;
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

    public String getDurationInTrafic() {
        return durationInTrafic;
    }

    public void setDurationInTrafic(String durationInTrafic) {
        this.durationInTrafic = durationInTrafic;
    }
}
