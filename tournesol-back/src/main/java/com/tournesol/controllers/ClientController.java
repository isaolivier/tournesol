package com.tournesol.controllers;

import com.tournesol.bean.ClientBean;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.tournesol.utils.BeanBuilder.*;

/**
 * Created by redeyed on 4/22/17.
 */

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Recherche de l'ensemble des clients correspondant à un nom.
     * @param name
     * @return
     */
    @GetMapping("/client")
    public Iterable<ClientBean> greeting() {

        Iterable<ClientEntity> clientEntities = clientRepository.findAll();

        return StreamSupport.stream(clientEntities.spliterator(), false)
                .map(c -> buildClient(c))
                .collect(Collectors.toList());
    }

    @PutMapping("/client")
    public void createClient(ClientBean client) {

        clientRepository.save(ClientEntity.builder()
                .nom(client.getNom())
                .telephone(client.getTelephone())
                .portable(client.getNom())
                .email(client.getEmail())
                .note(client.getNote())
                .build());
    }

    @PostMapping("/client")
    public void updateClient(ClientBean client) {

        clientRepository.save(ClientEntity.builder()
                .id(client.getId())
                .nom(client.getNom())
                .telephone(client.getTelephone())
                .portable(client.getNom())
                .email(client.getEmail())
                .note(client.getNote())
                .build());
    }
}
