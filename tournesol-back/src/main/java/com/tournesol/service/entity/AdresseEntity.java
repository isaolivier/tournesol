package com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by iolivier on 14/04/2017.
 */
@Data
@Entity
@Table(name = "ADRESSE")
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
