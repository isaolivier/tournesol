package com.tournesol.com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
@Entity
public class Appareil {

    /**
     * Adresse où l'appareil est entretenu.
     */
    private String adresse;

    /**
     * Marque de l'appareil.
     */
    private String marque;

    /**
     * Numéro de série.
     */
    private String numeroDeSerie;

    /**
     * Date d'installation.
     */
    private Date dateInstallation;

    /**
     * Date de mise en service.
     */
    private Date dateMiseEnService;
}
