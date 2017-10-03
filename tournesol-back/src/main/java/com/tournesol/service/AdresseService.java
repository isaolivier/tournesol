package com.tournesol.service;

import com.google.maps.model.PlaceDetails;
import com.tournesol.mapper.AdresseMapper;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.google.PlaceService;
import com.tournesol.service.repository.AdresseRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdresseService {

    private AdresseRepository adresseRepository;

    private PlaceService placeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdresseService.class);

    @Autowired
    public AdresseService(AdresseRepository adresseRepository, PlaceService placeService) {
        this.adresseRepository = adresseRepository;
        this.placeService = placeService;
    }

    /**
     * Retourne les informations détaillées d'une adresse, en priorité venant de la base locale.
     * Si celle-ci ne contient pas les informations, on fait appel aux services google.
     */
    public AdresseEntity findAdresseDetails(String placeId, Long adresseId) throws Exception {
        AdresseEntity adresseEntity = null;
        if (adresseId != null) {
            adresseEntity = adresseRepository.findById(adresseId).orElse(null);
        }

        if (adresseEntity == null || !StringUtils.equals(placeId, adresseEntity.getPlaceId())) {
            final PlaceDetails placeDetails = placeService.getPlaceDetails(placeId);
            adresseEntity = AdresseMapper.map(placeDetails);
        }
        return adresseEntity;
    }
}
