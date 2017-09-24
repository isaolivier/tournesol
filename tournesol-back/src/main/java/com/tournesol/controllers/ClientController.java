package com.tournesol.controllers;

import com.google.maps.model.PlaceDetails;
import com.tournesol.bean.output.ClientOutputBean;
import com.tournesol.bean.input.ClientInputBean;
import com.tournesol.mapper.AdresseMapper;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.service.MapService;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.repository.ClientRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
                .map(c -> ClientMapper.INSTANCE.clientEntityToClientOutpuBean(c))
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/client")
    public void updateClient(@RequestBody ClientInputBean client) throws Exception {

        final ClientEntity clientEntity = ClientMapper.INSTANCE.clientInputeBeanToClientEntity(client);

        ClientEntity existingClient = clientRepository.findById(client.getId()).orElse(null);

        if (existingClient != null) {
            if (!StringUtils.equals(client.getPlaceId(), existingClient.getAdresse().getPlaceId())) {
                final PlaceDetails placeDetails = mapService.getPlaceDetails(client.getPlaceId());

                AdresseEntity adresseEntity = AdresseMapper.map(placeDetails);
                adresseEntity.setPlaceId(client.getPlaceId());
                adresseEntity.setId(existingClient.getAdresse().getId());

                clientEntity.setAdresse(adresseEntity);
            }

            clientRepository.save(clientEntity);
        }
    }

    @PostMapping("/client")
    public void createClient(@RequestBody ClientInputBean client) throws Exception {

        final ClientEntity clientEntity = ClientMapper.INSTANCE.clientInputeBeanToClientEntity(client);

        final PlaceDetails placeDetails = mapService.getPlaceDetails(client.getPlaceId());

        AdresseEntity adresseEntity = AdresseMapper.map(placeDetails);
        adresseEntity.setPlaceId(client.getPlaceId());

        clientEntity.setAdresse(adresseEntity);

        clientRepository.save(clientEntity);
    }

}
