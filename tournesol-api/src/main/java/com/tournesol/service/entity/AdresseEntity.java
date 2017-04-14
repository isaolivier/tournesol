package com.tournesol.service.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by iolivier on 14/04/2017.
 */
public class AdresseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "voie", nullable = false)
    private String voie;

    @Column(name = "codePostal", nullable = false)
    private String codePostal;

    @Column(name = "codePays")
    private String codePays;
}
