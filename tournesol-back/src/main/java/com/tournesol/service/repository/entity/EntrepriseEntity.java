package com.tournesol.service.repository.entity;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by iolivier on 10/04/2017.
 */
@Entity
@Table(name = "ENTREPRISE")
public class EntrepriseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "siret")
    private String siret;

    /**
     * Heure d'ouverture.
     */
    @Column(name = "heure_debut")
    private LocalTime heureDebut;

    /**
     * Heure de fermeture.
     */
    @Column(name = "heure_fin")
    private LocalTime heureFin;

    /**
     * Indexes des jours d'ouverture de l'entrerpise.
     * 0, 1, 2, 3, 4 pour lundi, mardi, mercredi jeudi, vendredi.
     */
    @Column(name = "jours_ouverture")
    private int[] joursOuverture;
}
