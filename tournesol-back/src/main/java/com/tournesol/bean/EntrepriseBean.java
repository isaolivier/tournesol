package com.tournesol.bean;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

/**
 * Created by iolivier on 25/04/2017.
 */
@Data
@Builder
public class EntrepriseBean {

    private Long id;

    private String nom;

    private String siret;
}
