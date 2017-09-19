package com.tournesol.mapper;

import com.google.maps.model.AddressComponentType;
import com.google.maps.model.PlaceDetails;
import com.tournesol.service.entity.AdresseEntity;

import java.util.Arrays;

import org.mapstruct.Mapper;

@Mapper
public class AdresseMapper {

    public static AdresseEntity map(PlaceDetails placeDetails) {

        AdresseEntity adresseEntity = new AdresseEntity();

        adresseEntity.setNumero(getDetailProperty(placeDetails, AddressComponentType.STREET_NUMBER));
        adresseEntity.setVoie(getDetailProperty(placeDetails, AddressComponentType.ROUTE));
        adresseEntity.setCommune(getDetailProperty(placeDetails, AddressComponentType.LOCALITY));
        adresseEntity.setCodePostal(getDetailProperty(placeDetails, AddressComponentType.POSTAL_CODE));

        adresseEntity.setLatitude(placeDetails.geometry.location.lat);
        adresseEntity.setLongitude(placeDetails.geometry.location.lng);
        adresseEntity.setPlaceId(placeDetails.placeId);

        return adresseEntity;
    }

    private static String getDetailProperty(PlaceDetails placeDetails, AddressComponentType type) {

        return Arrays.stream(placeDetails.addressComponents)
                .filter(a -> Arrays.stream(a.types).anyMatch(t -> t == type))
                .findFirst()
                .map(e -> e.longName)
                .orElse(null);
    }

}
