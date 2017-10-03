package com.tournesol.service;

import com.google.api.services.calendar.model.Event;
import com.google.maps.model.PlaceDetails;
import com.tournesol.mapper.AdresseMapper;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.google.PlaceService;
import com.tournesol.util.DistanceCalculator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlyDistanceService {

    private PlaceService placeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlyDistanceService.class);

    @Autowired
    public FlyDistanceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Calcule la distance entre l'adresse souhaitée du rdv et celle d'un rdv google.
     */
    public Double calculateFlyDistance(Double latitude, Double longitude, Event googleEvent) {

        Double result = 10000d;

        Double targetLatitude = null;
        Double targetLongitude = null;

        if (googleEvent.getExtendedProperties() == null
                || googleEvent.getExtendedProperties().getShared().isEmpty()
                || !googleEvent.getExtendedProperties().getShared().containsKey("latitude")
                || !googleEvent.getExtendedProperties().getShared().containsKey("longitude")) {

            LOGGER.info("Le rendez-vous du " + googleEvent.getStart() + "[ " + googleEvent.getSummary() + " ] ne contient pas les propriétés latitude et longitude, nous effectuons le calcul à partir de location saisie");

            if (StringUtils.isNotEmpty(googleEvent.getLocation())) {
                final PlaceDetails place = placeService.findPlace(googleEvent.getLocation());

                if (place == null) {
                    LOGGER.warn("Impossible de caluler la distance pour ce rdv, par rapport à l'adresse qu'il contient (" + googleEvent.getLocation() + ")");
                } else {
                    final AdresseEntity targetAdresse = AdresseMapper.map(place);
                    targetLatitude = targetAdresse.getLatitude();
                    targetLongitude = targetAdresse.getLongitude();
                }
            }
        } else {
            targetLatitude = Double.valueOf(googleEvent.getExtendedProperties().getShared().get("latitude"));
            targetLongitude = Double.valueOf(googleEvent.getExtendedProperties().getShared().get("longitude"));
        }

        if (targetLatitude != null && targetLongitude != null) {
            result = DistanceCalculator.distance(latitude, longitude, targetLatitude, targetLongitude, "K");
        }

        return result;
    }
}
