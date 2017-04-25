package com.tournesol.controllers;

import com.tournesol.bean.EntrepriseBean;
import com.tournesol.service.entity.EntrepriseEntity;
import com.tournesol.service.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by redeyed on 4/22/17.
 */

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    /**
     * Recherche de l'ensemble des entreprises correspondant à un nom.
     * @param name
     * @return
     */
    @GetMapping("/entreprise")
    public Iterable<EntrepriseBean> greeting(@RequestParam(value="name", defaultValue="World") String name) {

        Iterable<EntrepriseEntity> entrepriseEntities = entrepriseRepository.findAll();

        return StreamSupport.stream(entrepriseEntities.spliterator(), false)
                .map(e -> EntrepriseBean.builder()
                        .id(e.getId())
                        .nom(e.getNom())
                        .siret(e.getSiret())
                        .build())
                .collect(Collectors.toList());
    }

}
