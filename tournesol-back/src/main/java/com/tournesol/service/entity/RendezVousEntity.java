package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RENDEZVOUS")
public class RendezVousEntity extends BaseEntity {

    @ManyToOne(targetEntity = AppareilEntity.class)
    private AppareilEntity appareil;

    @OneToOne(targetEntity = ClientEntity.class)
    private ClientEntity client;

    @Column(name = "event_cal_id")
    private String eventId;

    public AppareilEntity getAppareil() {
        return appareil;
    }

    public void setAppareil(AppareilEntity appareil) {
        this.appareil = appareil;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
