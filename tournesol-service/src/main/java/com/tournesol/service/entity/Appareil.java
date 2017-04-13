package com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
@Entity
public class Appareil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @ManyToOne
    private Client client;
}
