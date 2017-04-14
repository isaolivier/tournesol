package com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
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
}
