package com.tournesol.controllers;

import com.google.maps.model.AddressComponentType;
import com.google.maps.model.PlaceDetails;
import com.tournesol.bean.ClientOutputBean;
import com.tournesol.bean.input.ClientInputBean;
import com.tournesol.mapper.ClientInputMapper;
import com.tournesol.mapper.ClientOutputMapper;
import com.tournesol.service.MapService;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.repository.ClientRepository;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by redeyed on 4/22/17.
 */

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MapService mapService;

    /**
     * Recherche de l'ensemble des clients.
     *
     * @return
     */
    @GetMapping("/client")
    public Iterable<ClientOutputBean> greeting() {

        Iterable<ClientEntity> clientEntities = clientRepository.findAll();

        return StreamSupport.stream(clientEntities.spliterator(), false)
                .map(c -> ClientOutputMapper.INSTANCE.map(c))
                .collect(Collectors.toList());
    }

    @PutMapping("/client")
    public void updateClient(@RequestBody ClientInputBean client) {
        final ClientEntity clientEntity = ClientInputMapper.INSTANCE.map(client);

        clientRepository.save(clientEntity);
    }

    @PostMapping("/client")
    public void createClient(@RequestBody ClientInputBean client) throws Exception {
        final ClientEntity clientEntity = ClientInputMapper.INSTANCE.map(client);

        final PlaceDetails placeDetails = mapService.getPlaceDetails(client.getPlaceId());

        AdresseEntity adresseEntity = new AdresseEntity();
        clientEntity.setAdresse(adresseEntity);

        adresseEntity.setPlaceId(client.getPlaceId());
        adresseEntity.setNumero(getDetailProperty(placeDetails, AddressComponentType.STREET_NUMBER));
        adresseEntity.setVoie(getDetailProperty(placeDetails, AddressComponentType.ROUTE));
        adresseEntity.setCommune(getDetailProperty(placeDetails, AddressComponentType.LOCALITY));
        adresseEntity.setCodePostal(getDetailProperty(placeDetails, AddressComponentType.POSTAL_CODE));

        adresseEntity.setLatitude(placeDetails.geometry.location.lat);
        adresseEntity.setLongitude(placeDetails.geometry.location.lng);
        adresseEntity.setPlaceId(placeDetails.placeId);

        clientRepository.save(clientEntity);
    }

    private String getDetailProperty(PlaceDetails placeDetails, AddressComponentType type) {

        return Arrays.stream(placeDetails.addressComponents)
                .filter(a -> Arrays.stream(a.types).anyMatch(t -> t == type))
                .findFirst()
                .map(e -> e.longName)
                .orElse(null);
    }
}
