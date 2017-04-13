package com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nom du client
     */
    private String nom;

    /**
     * Numéro de téléphone fixe
     */
    private String telephone;

    /**
     * Numéro de téléphone mobile.
     */
    private String portable;

    /**
     * Email.
     */
    private String email;

    /**
     * Note du client : entier de 1 à 5.
     */
    private int vote;

    /**
     * Appareils du client.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "CLIENT_ID")
    private List<Appareil> appareils;

}
