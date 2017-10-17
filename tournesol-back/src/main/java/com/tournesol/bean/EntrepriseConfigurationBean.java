package com.tournesol.bean;

import java.time.LocalTime;

public class EntrepriseConfigurationBean {

    /**
     * Heure d'ouverture.
     */
    private LocalTime heureOuverture;

    /**
     * Heure de fermeture.
     */
    private LocalTime heureFermeture;

    /**
     * Indexes des jours d'ouverture de l'entrerpise.
     * 0, 1, 2, 3, 4 pour lundi, mardi, mercredi jeudi, vendredi.
     */
    private Integer[] joursOuverture;

    /**
     * Steps utilisés pour l'affichage des heures dans le form de prise de rdv.
     */
    private int timeStep;

    /**
     * Temps d'un rdv par défaut en minutes.
     */
    private int tempsRdv;

    /**
     * Nombre de jours dans le futur sur lesquels est effectué la recherche de créneaux disponibles.
     */
    private int searchDays;

    /**
     * Rayon de recherche en km des rdvs proches
     */
    private int searchDistance;

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

    public Integer[] getJoursOuverture() {
        return joursOuverture;
    }

    public void setJoursOuverture(Integer[] joursOuverture) {
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
}
