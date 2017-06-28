package com.tournesol.controllers;

import com.tournesol.bean.AppareilBean;
import com.tournesol.mapper.AppareilMapper;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.repository.AppareilRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .map(a -> AppareilMapper.INSTANCE.appareilToAppareilBean(a))
                .collect(Collectors.toList());
    }


}
