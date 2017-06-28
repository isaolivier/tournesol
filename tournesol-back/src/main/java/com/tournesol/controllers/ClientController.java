package com.tournesol.controllers;

import com.tournesol.bean.ClientBean;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.repository.ClientRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by redeyed on 4/22/17.
 */

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Recherche de l'ensemble des clients.
     * @return
     */
    @GetMapping("/client")
    public Iterable<ClientBean> greeting() {

        Iterable<ClientEntity> clientEntities = clientRepository.findAll();

        return StreamSupport.stream(clientEntities.spliterator(), false)
                .map(c -> ClientMapper.INSTANCE.clientToClientBean(c))
                .collect(Collectors.toList());
    }

    @PutMapping("/client")
    public void createClient(ClientBean client) {
        final ClientEntity clientEntity = ClientMapper.INSTANCE.clientBeanToClient(client);
        clientRepository.save(clientEntity);
    }

    @PostMapping("/client")
    public void updateClient(ClientBean client) {
        final ClientEntity clientEntity = ClientMapper.INSTANCE.clientBeanToClient(client);
        clientRepository.save(clientEntity);
    }
}
