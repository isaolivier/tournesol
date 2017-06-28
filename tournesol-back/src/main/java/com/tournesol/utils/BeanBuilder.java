package com.tournesol.utils;

import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.ClientBean;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;

/**
 * Created by iolivier on 25/04/2017.
 */
public class BeanBuilder {

    private BeanBuilder() { }

    /**

     * Transformation d'une entité client en bean.
     * @param client
     * @return
     */
    public static ClientBean buildClient(ClientEntity client) {

        return ClientBean.builder()
                .id(client.getId())
                .civilite(client.getCivilite())
                .nom(client.getNom())
                .telephone(client.getTelephone())
                .portable(client.getPortable())
                .email(client.getEmail())
                .note(client.getNote())
                .build();
    }

    /**
     * Transformation d'une entité adresse en bean.
     * @param adresse
     * @return
     */
    public static AdresseBean buildAdresse(AdresseEntity adresse) {

        return AdresseBean.builder()
                .id(adresse.getId())
                .adresse(adresse.getNumeroVoie())
                .codePostal(adresse.getCodePostal())
                .build();
    }
}
