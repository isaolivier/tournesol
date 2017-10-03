package com.tournesol.service.google;

import com.google.api.services.calendar.model.Event;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.Unit;
import com.tournesol.config.GoogleConfiguration;
import com.tournesol.mapper.AdresseMapper;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.util.DistanceCalculator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service de renvoie des données de distance et temps de trajet entre 2 points.
 */
@Component
public class DistanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceService.class);

    @Autowired
    private GoogleConfiguration googleConfiguration;

    private GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(googleConfiguration.getApiKey())
            .build();


    public DistanceMatrix getTimeDistance(String originPlaceId, String destinationPlaceId) {

        String[] origins = new String[] {"place_id:" + originPlaceId};
        String[] destinations = new String[] {"place_id:" + destinationPlaceId};

        try {
            DistanceMatrixApiRequest distanceMatrixApiRequest = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations)
                    .language("fr")
                    .units(Unit.METRIC);

            return distanceMatrixApiRequest.await();
        } catch (Exception e) {
            LOGGER.warn("Could not calculate distance between %s and %s", originPlaceId, destinationPlaceId );
        }

        return null;
    }
}
