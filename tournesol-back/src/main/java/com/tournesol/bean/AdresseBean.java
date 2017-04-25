package com.tournesol.bean;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by iolivier on 25/04/2017.
 */
@Builder
@Data
public class AdresseBean {

    private Long id;

    private String numero;

    private String voie;

    private String codePostal;

    private String codePays;
}
