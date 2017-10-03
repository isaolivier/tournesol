package com.tournesol.service.google;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.PlaceDetails;
import com.tournesol.config.GoogleConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceService.class);

    private GeoApiContext context;

    @Autowired
    public PlaceService(GoogleConfiguration googleConfiguration) {
        this.context = new GeoApiContext.Builder()
                .apiKey(googleConfiguration.getApiKey())
                .build();
    }

    /**
     * Recherche des informations détaillées pour un emplacement précis.
     */
    public PlaceDetails getPlaceDetails(String placeId) throws Exception {

        return PlacesApi.placeDetails(context, placeId).await();
    }

    /**
     * Recherche d'un point précis par rapport à une adresse saisie.
     */
    public PlaceDetails findPlace(String query) {

        PlaceDetails result = null;
        try {
            final AutocompletePrediction[] predictions = PlacesApi.placeAutocomplete(context, query).await();

            if (predictions == null || predictions.length == 0) {
                LOGGER.error("Aucune correspondance n'a été trouvée pour la recherche suivante [" + query + "], veuillez resaisir une adresse via l'application");
            } else if (predictions.length > 1) {
                LOGGER.error("Plusieurs possbilités ont été trouvées pour la recherche suivante [" + query + "], veuillez resaisir une adresse via l'application");
            } else {

                result = getPlaceDetails(predictions[0].placeId);
                LOGGER.info("Place trouvé : " + predictions[0].description);
            }

        } catch (Exception e) {
            LOGGER.error("Erreur lors de la recherche d'adresse.");
        }

        return result;
    }
}
