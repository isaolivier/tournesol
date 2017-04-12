package com.tournesol.com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
@Entity
public class Client {

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

}
