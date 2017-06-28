package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by iolivier on 14/04/2017.
 */
@Entity
@Table(name = "ADRESSE")
public class AdresseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "numeroVoie")
    private String numeroVoie;

    @Column(name = "codePostal", nullable = false)
    private String codePostal;

}
