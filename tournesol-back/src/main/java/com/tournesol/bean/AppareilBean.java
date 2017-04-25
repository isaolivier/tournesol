package com.tournesol.bean;

import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by iolivier on 25/04/2017.
 */
@Data
@Builder
public class AppareilBean {

    private Long id;

    private AdresseBean adresse;

    private String marque;

    private String numeroDeSerie;

    private Date dateInstallation;

    private Date dateMiseEnService;

    private ClientBean client;
}
