package com.tournesol.bean;

import java.io.Serializable;

/**
 * Created by iolivier on 25/04/2017.
 */
public class EntrepriseBean  implements Serializable {

    private Long id;

    private String nom;

    private String siret;

    private AdresseBean adresse;

    private EntrepriseConfigurationBean configuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public EntrepriseConfigurationBean getConfiguration() {
        return configuration;
    }

    public void setConfiguration(EntrepriseConfigurationBean configuration) {
        this.configuration = configuration;
    }

    public AdresseBean getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseBean adresse) {
        this.adresse = adresse;
    }
}
