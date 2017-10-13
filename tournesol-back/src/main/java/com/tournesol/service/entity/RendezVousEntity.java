package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @SequenceGenerator(name="my_seq", sequenceName="rendezvous_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = SEQUENCE, generator = "my_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(targetEntity = AppareilEntity.class)
    private AppareilEntity appareil;

    @OneToOne(targetEntity = ClientEntity.class)
    private ClientEntity client;

    @Column(name = "event_cal_id")
    private String eventId;

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

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
