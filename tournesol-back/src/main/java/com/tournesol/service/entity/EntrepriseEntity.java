package com.tournesol.service.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by iolivier on 10/04/2017.
 */
@Entity
@Table(name = "ENTREPRISE")
public class EntrepriseEntity extends BaseEntity {
	
	private static final long serialVersionUID = 4988724258162598786L;

    @Id
    @SequenceGenerator(name="my_seq", sequenceName="entreprise_sequence", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = SEQUENCE, generator = "my_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "siret")
    private String siret;

    /**
     * Heure d'ouverture.
     */
    @Column(name = "heure_ouverture")
    private LocalTime heureOuverture;

    /**
     * Heure de fermeture.
     */
    @Column(name = "heure_fermeture")
    private LocalTime heureFermeture;

    /**
     * Indexes des jours d'ouverture de l'entrerpise.
     * 0, 1, 2, 3, 4 pour lundi, mardi, mercredi jeudi, vendredi.
     */
    @Column(name = "jours_ouverture")
    private byte joursOuverture;

    /**
     * Steps utilisés pour l'affichage des heures dans le form de prise de rdv.
     */
    @Column(name = "time_step")
    private int timeStep;

    /**
     * Temps d'un rdv par défaut en minutes.
     */
    @Column(name = "temps_rdv")
    private int tempsRdv;

    /**
     * Nombre de jours dans le futur sur lesquels est effectué la recherche de créneaux disponibles.
     */
    @Column(name = "search_days")
    private int searchDays;

    /**
     * Rayon de recherche en km des rdvs proches
     */
    @Column(name = "search_distance")
    private int searchDistance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    public void setHeureOuverture(LocalTime heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }

    public void setHeureFermeture(LocalTime heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public byte getJoursOuverture() {
        return joursOuverture;
    }

    public List<DayOfWeek> getListJoursOuverture (){
        final List<DayOfWeek> days = new ArrayList<>();
        int daysMask = getJoursOuverture();

        for (int i = 0; i <7; i++){
            int value = daysMask & 0x01;
            daysMask = daysMask >> 1;
            if (value == 1) {
                days.add(DayOfWeek.of(7 - i));
            }
        }
        return days;
    }

    public void setJoursOuverture(byte joursOuverture) {
        this.joursOuverture = joursOuverture;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public int getTempsRdv() {
        return tempsRdv;
    }

    public void setTempsRdv(int tempsRdv) {
        this.tempsRdv = tempsRdv;
    }

    public int getSearchDays() {
        return searchDays;
    }

    public void setSearchDays(int searchDays) {
        this.searchDays = searchDays;
    }

    public int getSearchDistance() {
        return searchDistance;
    }

    public void setSearchDistance(int searchDistance) {
        this.searchDistance = searchDistance;
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
