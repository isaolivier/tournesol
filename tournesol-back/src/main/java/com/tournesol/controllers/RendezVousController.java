package com.tournesol.controllers;

import com.tournesol.bean.AuthInfo;
import com.tournesol.bean.RendezVousBean;
import com.tournesol.bean.input.RendezVousInputBean;
import com.tournesol.mapper.AppareilMapper;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.mapper.EventBeanMapper;
import com.tournesol.service.entity.AppareilEntity;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public Iterable<RendezVousBean> greeting(@RequestHeader(value = "uid", required = true) String uid,
                                             @RequestHeader(value = "email", required = true) String email,
                                             @RequestParam(value = "date", required = false) String date) {

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUID(uid);
        authInfo.setEmail(email);

        /*
            Recherche des évenements dans le calendrier distant (google)
         */
        final Map<String, EventEntity> eventMap = eventService.getEvents(authInfo, ZonedDateTime.now()).stream()
                .collect(Collectors.toMap(e -> e.getICalUID(), e -> e));

        /*
            Recherche des rdvs dans la base locale,
            Regroupement par eventId.
         */
        final Map<String, List<RendezVousEntity>> rendezVousEntities = rdvRepository.findRendezVousEntities(new ArrayList<>(eventMap.keySet())).stream()
                .collect(Collectors.groupingBy(RendezVousEntity::getEventId, Collectors.toList()));

        /*
            Aggrégation des données
         */
        return rendezVousEntities.entrySet().stream()
                .map(e -> buildRendezVousBean(e.getValue(), eventMap.get(e.getKey())))
                .collect(Collectors.toList());
    }

    @PutMapping("/rdv")
    public void create(@RequestHeader(value = "uid", required = true) String uid,
                         @RequestHeader(value = "email", required = true) String email,
                         @RequestBody RendezVousInputBean rdv) {

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUID(uid);
        authInfo.setEmail(email);

        /*
            Création de l'évenement dans le calendrier distant
         */
        final EventEntity event = eventService.createEvent(authInfo, EventBeanMapper.INSTANCE.eventBeanToEventEntity(rdv.getEvent()));

        rdv.getAppareils().stream().forEach(a -> {
            /*
              Création d'un rendez-vous en base pour chacun des appareils
             */
            RendezVousEntity rdvEntity = new RendezVousEntity();
            rdvEntity.setEventId(event.getICalUID());

            rdvEntity.setAppareil(new AppareilEntity());
            rdvEntity.getAppareil().setId(a);

            rdvRepository.save(rdvEntity);
        });
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

        rdv.setEvent(EventBeanMapper.INSTANCE.eventEntityToEventBean(eventEntity));

        return rdv;
    }
}
