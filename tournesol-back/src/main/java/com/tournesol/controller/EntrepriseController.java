package com.tournesol.controller;

import com.tournesol.bean.EntrepriseBean;
import com.tournesol.mapper.EntrepriseMapper;
import com.tournesol.service.entity.EntrepriseEntity;
import com.tournesol.service.repository.EntrepriseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by redeyed on 4/22/17.
 */

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    /**
     * Recherche de lentreprise.
     */
    @GetMapping("/entreprise")
    public EntrepriseBean greeting() {

        Iterable<EntrepriseEntity> entrepriseEntities = entrepriseRepository.findAll();

        return EntrepriseMapper.INSTANCE.map(entrepriseEntities.iterator().next());
    }

}
