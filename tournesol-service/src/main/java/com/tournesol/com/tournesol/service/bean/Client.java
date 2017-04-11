package com.tournesol.com.tournesol.service.bean;

import lombok.Data;

/**
 * Created by iolivier on 10/04/2017.
 */
@Data
public class Client {

    private String name;

    /**
     * Numéro de téléphone fixe
     */
    private String phoneNumber;

    /**
     * Numéro de téléphone mobile.
     */
    private String mobilePhoneNumber;

    /**
     * Email.
     */
    private String email;

    /**
     * Note du client : entier de 1 à 5.
     */
    private int vote;

}
