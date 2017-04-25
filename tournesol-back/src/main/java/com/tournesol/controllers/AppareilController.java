package com.tournesol.controllers;

import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.AppareilBean;
import com.tournesol.bean.ClientBean;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.entity.EntrepriseEntity;
import com.tournesol.service.repository.AppareilRepository;
import com.tournesol.utils.BeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.tournesol.utils.BeanBuilder.*;
import static com.tournesol.utils.BeanBuilder.buildClient;

/**
 * Created by iolivier on 25/04/2017.
 */
@RestController
public class AppareilController {

    @Autowired
    private AppareilRepository appareilRepository;

    /**
     * Recherche de l'ensemble des appareils
     * @return
     */
    @GetMapping("/appareil")
    public Iterable<AppareilBean> greeting() {

        Iterable<AppareilEntity> appareilEntities = appareilRepository.findAll();

        return StreamSupport.stream(appareilEntities.spliterator(), false)
                .map(a -> AppareilBean.builder()
                            .dateInstallation(a.getDateInstallation())
                            .dateMiseEnService(a.getDateMiseEnService())
                            .adresse(buildAdresse(a.getAdresse()))
                            .client(buildClient(a.getClient()))
                            .build())
                .collect(Collectors.toList());
    }


}
