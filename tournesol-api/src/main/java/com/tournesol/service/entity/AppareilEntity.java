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
@Table(name = "APPAREIL")
public class AppareilEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Adresse où l'appareil est entretenu.
     */
    @OneToOne(targetEntity = AdresseEntity.class)
    private AdresseEntity adresse;

    /**
     * Marque de l'appareil.
     */
    @Column(name = "marque")
    private String marque;

    /**
     * Numéro de série.
     */
    @Column(name = "numero_serie")
    private String numeroDeSerie;

    /**
     * Date d'installation.
     */
    @Column(name = "date_installation")
    private Date dateInstallation;

    /**
     * Date de mise en service.
     */
    @Column(name = "date_mise_en_service")
    private Date dateMiseEnService;

    @ManyToOne(targetEntity = ClientEntity.class)
    private ClientEntity client;
}
