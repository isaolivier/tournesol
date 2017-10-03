package com.tournesol.controller;

import com.google.maps.model.PlaceDetails;
import com.tournesol.mapper.AdresseMapper;
import com.tournesol.service.google.PlaceService;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.repository.AdresseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdresseController {

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private PlaceService placeService;

    @GetMapping("/adresse")
    public void completeAddresses() {

        final List<AdresseEntity> adresseEntities = adresseRepository.findByPlaceIdIsNull();

        final Map<Long, String> queries = adresseEntities.stream()
                .collect(Collectors.toMap(a -> a.getId(),
                        a -> new StringJoiner(" ")
                                .add(a.getNumero())
                                .add(a.getVoie())
                                .add(a.getCodePostal())
                                .add(a.getCommune())
                                .add("France")
                                .toString()));

        final List<AdresseEntity> adresseEntitiesToSave = new ArrayList<>();

        queries.entrySet().stream().forEach(e -> {
            final PlaceDetails place = placeService.findPlace(e.getValue());

            if(place != null) {
                AdresseEntity adresseEntity = AdresseMapper.map(place);
                adresseEntity.setId(e.getKey());
                adresseEntitiesToSave.add(adresseEntity);
            }

        });

        adresseRepository.saveAll(adresseEntitiesToSave);
    }
}
