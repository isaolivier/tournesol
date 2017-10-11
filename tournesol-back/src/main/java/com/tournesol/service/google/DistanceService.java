package com.tournesol.service.google;

import com.google.common.collect.Lists;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Unit;
import com.tournesol.bean.Coordonnees;
import com.tournesol.config.GoogleConfiguration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de renvoie des données de distance et temps de trajet entre 2 points.
 */
@Service
public class DistanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceService.class);

    private GeoApiContext context;

    @Autowired
    public DistanceService(GoogleConfiguration googleConfiguration) {
        this.context = new GeoApiContext.Builder()
                .apiKey(googleConfiguration.getApiKey())
                .build();
    }

    public DistanceMatrix getDurationAndDistance(LocalDateTime arrivalTime, Coordonnees origins, Coordonnees destinations) {

        return getDurationAndDistance(arrivalTime, Lists.newArrayList(origins), Lists.newArrayList(destinations));
    }

    public DistanceMatrix getDurationAndDistance(LocalDateTime arrivalTime, List<Coordonnees> origins, List<Coordonnees> destinations) {

        List<String> originsParam = origins.stream()
                .map(c -> c.getLatitude() + "," + c.getLongitude())
                .collect(Collectors.toList());

        List<String> destinationsParam = destinations.stream()
                .map(c -> c.getLatitude() + "," + c.getLongitude())
                .collect(Collectors.toList());

        try {
            DistanceMatrixApiRequest distanceMatrixApiRequest = DistanceMatrixApi.getDistanceMatrix(context,
                    originsParam.toArray(new String[0]),
                    destinationsParam.toArray(new String[0]))
                    .arrivalTime(new DateTime(arrivalTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                    .language("fr")
                    .units(Unit.METRIC);

            return distanceMatrixApiRequest.await();
        } catch (Exception e) {
            LOGGER.warn("Could not calculate distance with google", e);
        }

        return null;
    }
}
