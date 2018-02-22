package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by iolivier on 14/04/2017.
 */
@Entity
@Table(name = "ADRESSE")
public class AdresseEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name="adresse_sequence", sequenceName="adresse_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = SEQUENCE, generator = "adresse_sequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "voie")
    private String voie;

    @Column(name = "codePostal")
    private String codePostal;

    @Column(name = "commune")
    private String commune;

    @Column(name = "placeId")
    private String placeId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVoie() {
        return voie;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
