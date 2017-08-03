package com.tournesol.controllers;

import com.tournesol.bean.RendezVousBean;
import com.tournesol.mapper.AppareilMapper;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.mapper.EventMapper;
import com.tournesol.service.entity.EventEntity;
import com.tournesol.service.entity.RendezVousEntity;
import com.tournesol.service.events.EventService;
import com.tournesol.service.repository.RendezVousRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller chargé de la gestion / rechercher des rendez-vous.
 */
@RestController
public class RendezVousController {

    @Autowired
    private RendezVousRepository rdvRepository;

    @Autowired
    private EventService eventService;

    /**
     * Recherche de l'ensemble des rendez-vous sur une journée donnée.
     *
     * @param date, date de recherche des rendez-vous, si celle-ci n'est pas précisée c'est la date du jour qui sera utilisée.
     * @return la liste des rendez-vous du jour, aggrégeant l'ensemble des infos relatives au rdv (client, appareil ...)
     */
    @GetMapping("/rdvs")
    public Iterable<RendezVousBean> greeting(@RequestParam(value = "date", required = false) String date) {

        final Map<String, EventEntity> eventMap = eventService.getEvents(ZonedDateTime.now()).stream()
                .collect(Collectors.toMap(e -> e.getiCalUID(), e -> e));

        /*
            Recherche des rdvs dans la base locale,
            Regroupement par eventId.
         */
        final Map<String, List<RendezVousEntity>> rendezVousEntities = rdvRepository.findRendezVousEntities(new ArrayList<>(eventMap.keySet())).stream()
                .collect(Collectors.groupingBy(RendezVousEntity::getEventId, Collectors.toList()));

        return rendezVousEntities.entrySet().stream()
                .map(e -> buildRendezVousBean(e.getValue(), eventMap.get(e.getKey())))
                .collect(Collectors.toList());
    }

    /**
     * Construction de l'objet rendez-vous.
     */
    private RendezVousBean buildRendezVousBean(List<RendezVousEntity> rdvEntities, EventEntity eventEntity) {

        RendezVousBean rdv = new RendezVousBean();

        rdvEntities.stream().forEach(r ->
            rdv.getAppareils().add(AppareilMapper.INSTANCE.appareilToAppareilBean(r.getAppareil()))
        );

        // Un rdv peut être pris sur plusieurs appareils mais pour un seul client,
        // on récupère donc le client correspondant au premier appareil
        rdv.setClient(ClientMapper.INSTANCE.clientToClientBean(rdvEntities.get(0).getAppareil().getClient()));

        rdv.setEvent(EventMapper.INSTANCE.eventToEventBean(eventEntity));

        return rdv;
    }
}
