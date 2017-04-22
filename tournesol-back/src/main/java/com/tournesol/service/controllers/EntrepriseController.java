package com.tournesol.service.controllers;

import com.tournesol.service.entity.EntrepriseEntity;
import com.tournesol.service.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by redeyed on 4/22/17.
 */

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @GetMapping("/entreprise")
    public Iterable<EntrepriseEntity> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return entrepriseRepository.findAll();
    }

}
