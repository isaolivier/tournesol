package com.tournesol.controllers;

import com.tournesol.bean.EntrepriseBean;
import com.tournesol.service.repository.EntrepriseRepository;

import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

//        Iterable<EntrepriseEntity> entrepriseEntities = entrepriseRepository.findAll();
//
//        return StreamSupport.stream(entrepriseEntities.spliterator(), false)
//                .map(e -> EntrepriseMapper.INSTANCE.entrepriseToEntrepriseBean(e))
//                .collect(Collectors.toList());

        final EntrepriseBean entrepriseBean = new EntrepriseBean();
        entrepriseBean.setId(1l);
        entrepriseBean.setNom("Samson Multi Services");
        entrepriseBean.setHeureDebut(LocalTime.of(8, 00));
        entrepriseBean.setHeureFin(LocalTime.of(18, 00));

        return Arrays.asList(entrepriseBean);
    }

}
