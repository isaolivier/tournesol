package com.tournesol.bean;

import java.sql.Date;

/**
 * Created by iolivier on 25/04/2017.
 */
public class AppareilBean {

    private Long id;

    private AdresseBean adresse;

    private String marque;

    private String numeroDeSerie;

    private Date dateInstallation;

    private Date dateMiseEnService;

    private ClientBean client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdresseBean getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseBean adresse) {
        this.adresse = adresse;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public Date getDateInstallation() {
        return dateInstallation;
    }

    public void setDateInstallation(Date dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    public Date getDateMiseEnService() {
        return dateMiseEnService;
    }

    public void setDateMiseEnService(Date dateMiseEnService) {
        this.dateMiseEnService = dateMiseEnService;
    }

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }
}
