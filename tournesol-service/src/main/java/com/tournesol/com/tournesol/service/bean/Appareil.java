package com.tournesol.com.tournesol.service.bean;

import lombok.Data;

import java.sql.Date;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
public class Appareil {

    /**
     * Adresse où l'appareil est entretenu.
     */
    private String address;

    /**
     * Marque de l'appareil.
     */
    private String trademark;

    /**
     * Numéro de série.
     */
    private String serialNumber;

    /**
     * Date d'installation.
     */
    private Date installationDate;

    /**
     * Date de mise en service.
     */
    private Date initialOperationDate;
}
