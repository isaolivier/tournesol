package com.tournesol.controller;

import com.tournesol.bean.AppareilBean;
import com.tournesol.mapper.AppareilMapper;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.repository.AppareilRepository;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iolivier on 25/04/2017.
 */
@RestController
public class AppareilController {

    @Autowired
    private AppareilRepository appareilRepository;

    /**
     * Recherche de l'ensemble des appareils pour un client donné.
     * @return
     */
    @GetMapping("/appareil/{clientId}")
    public Iterable<AppareilBean> greeting(@PathVariable String clientId) {

        Collection<AppareilEntity> appareilEntities = appareilRepository.findAppareilEntities(clientId);

        return appareilEntities.stream()
                .map(a -> AppareilMapper.INSTANCE.appareilEntityToAppareilBean(a))
                .collect(Collectors.toList());
    }


}
