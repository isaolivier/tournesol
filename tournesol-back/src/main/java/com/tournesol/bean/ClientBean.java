package com.tournesol.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by iolivier on 25/04/2017.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientBean {

    private Long id;

    private String nom;

    private String telephone;

    private String portable;

    private String email;

    private Integer note;

}
