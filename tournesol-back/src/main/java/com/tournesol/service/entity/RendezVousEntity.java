package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RENDEZVOUS")
public class RendezVousEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

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
