package com.tournesol.bean;

import com.tournesol.service.entity.AppareilEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by iolivier on 25/04/2017.
 */
@Builder
@Data
public class ClientBean {

    private Long id;

    private String nom;

    private String telephone;

    private String portable;

    private String email;

    private Integer note;

}
