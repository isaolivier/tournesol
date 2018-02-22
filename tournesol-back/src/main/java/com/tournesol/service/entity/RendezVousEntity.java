package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "RENDEZVOUS")
public class RendezVousEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name="rendezvous_sequence", sequenceName="rendezvous_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = SEQUENCE, generator = "rendezvous_sequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(targetEntity = AppareilEntity.class, fetch = FetchType.LAZY)
    private AppareilEntity appareil;

    @OneToOne(targetEntity = ClientEntity.class, fetch = FetchType.LAZY)
    private ClientEntity client;

    @Column(name = "event_cal_id")
    private String eventId;

    @Column(name = "client_id", insertable = false, updatable = false)
    private Long clientId;

    @Column(name = "appareil_id", insertable = false, updatable = false)
    private Long appareilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAppareilId() {
        return appareilId;
    }

    public void setAppareilId(Long appareilId) {
        this.appareilId = appareilId;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
