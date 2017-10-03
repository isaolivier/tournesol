package com.tournesol.controller;

import com.google.maps.model.PlaceDetails;
import com.tournesol.bean.input.ClientInputBean;
import com.tournesol.bean.output.ClientOutputBean;
import com.tournesol.mapper.AdresseMapper;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.google.PlaceService;
import com.tournesol.service.repository.ClientRepository;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by redeyed on 4/22/17.
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PlaceService placeService;

    /**
     * Recherche de l'ensemble des clients.
     *
     * @return
     */
    @GetMapping
    public Iterable<ClientOutputBean> greeting() {

        Iterable<ClientEntity> clientEntities = clientRepository.findAll();

        return StreamSupport.stream(clientEntities.spliterator(), false)
                .map(c -> ClientMapper.INSTANCE.clientEntityToClientOutpuBean(c))
                .sorted(Comparator.comparing(c -> c.getSociete() == null ? (c.getNom() == null ? "" : c.getNom()) : c.getSociete()))
                .collect(Collectors.toList());
    }

    @PutMapping
    public void updateClient(@RequestBody ClientInputBean client) throws Exception {

        ClientEntity existingClient = clientRepository.findById(client.getId()).orElse(null);

        ClientMapper.INSTANCE.updateClientEntityFromInputBean(client, existingClient);

        if (existingClient != null) {
            if (!StringUtils.equals(client.getPlaceId(), existingClient.getAdresse().getPlaceId())) {
                final PlaceDetails placeDetails = placeService.getPlaceDetails(client.getPlaceId());

                AdresseEntity adresseEntity = AdresseMapper.map(placeDetails);
                adresseEntity.setPlaceId(client.getPlaceId());
                adresseEntity.setId(existingClient.getAdresse().getId());

                existingClient.setAdresse(adresseEntity);
            }

            clientRepository.save(existingClient);
        }
    }

    @PostMapping
    public void createClient(@RequestBody ClientInputBean client) throws Exception {

        final ClientEntity clientEntity = ClientMapper.INSTANCE.clientInputeBeanToClientEntity(client);

        final PlaceDetails placeDetails = placeService.getPlaceDetails(client.getPlaceId());

        AdresseEntity adresseEntity = AdresseMapper.map(placeDetails);
        adresseEntity.setPlaceId(client.getPlaceId());

        clientEntity.setAdresse(adresseEntity);

        clientRepository.save(clientEntity);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) throws Exception {

        clientRepository.deleteById(clientId);
    }

}
