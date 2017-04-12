package com.tournesol.com.tournesol.service.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
@Entity
public class Entreprise {

    private String nom;

    private String siret;
}
