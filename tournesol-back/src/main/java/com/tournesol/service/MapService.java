package com.tournesol.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;

import org.springframework.stereotype.Service;

@Service
public class MapService {

    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyCXnZKQevQd4NxoQQhoW9QDimUvxk6Z800")
            .build();

    public PlaceDetails getPlaceDetails(String placeId) throws Exception {

        return PlacesApi.placeDetails(context, placeId).await();
    }
}
