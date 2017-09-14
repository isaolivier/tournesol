package com.tournesol.bean;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Created by iolivier on 25/04/2017.
 */
public class EntrepriseBean  implements Serializable {

    private Long id;

    private String nom;

    private String siret;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
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
}
