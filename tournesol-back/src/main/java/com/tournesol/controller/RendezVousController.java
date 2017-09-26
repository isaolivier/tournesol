package com.tournesol.controller;

import com.google.api.services.calendar.model.Event;
import com.tournesol.bean.AuthInfo;
import com.tournesol.bean.RendezVousBean;
import com.tournesol.bean.input.RendezVousInputBean;
import com.tournesol.bean.JourBean;
import com.tournesol.mapper.AppareilMapper;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.mapper.DateMapper;
import com.tournesol.mapper.EventMapper;
import com.tournesol.service.EventService;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.entity.RendezVousEntity;
import com.tournesol.service.repository.AdresseRepository;
import com.tournesol.service.repository.RendezVousRepository;
import com.tournesol.util.DistanceCalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private AdresseRepository adresseRepository;

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
                                             @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        /*
            Recherche des évenements dans le calendrier distant (google)
         */
        LocalDate dateRecherche = date == null ? LocalDate.now() : date;

        final Map<String, Event> eventMap = eventService.getEvents(authInfo, dateRecherche).stream()
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
        return eventMap.entrySet().stream()
                .map(e -> buildRendezVousBean(rendezVousEntities.get(e.getKey()), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche des dates optimales par rapport à un client donné.
     *
     * @param dayRange Nombre de jours sur lesquels on effectue une recherche des rdvs existants.
     * @param distanceRange Distance en max en km entre l'adresse et les adresses recharchées.
     * @param adresseId Identifiant de l'adresse à partir de laquelle effectuer une recherche.
     * @return la liste des rendez-vous du jour, aggrégeant l'ensemble des infos relatives au rdv (client, appareil ...)
     */
    @GetMapping("/rdv/search")
    public Iterable<JourBean> search(@RequestHeader(value = "uid", required = true) String uid,
                                     @RequestHeader(value = "email", required = true) String email,
                                     @RequestParam(value = "dayRange", required = true) int dayRange,
                                     @RequestParam(value = "distanceRange", required = true) int distanceRange,
                                     @RequestParam(value = "adresseId", required = true) Long adresseId) {

        final Map<LocalDate, JourBean> result = new HashMap<>();

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        AdresseEntity adresseEntity = adresseRepository.findById(adresseId).get();

        /*
            Recherche des évenements dans le calendrier distant (google)
         */
        final Map<String, Event> eventMap = eventService.getEvents(authInfo, LocalDate.now(), distanceRange).stream()
                .collect(Collectors.toMap(e -> e.getICalUID(), e -> e));

        eventMap.values().stream()
                .filter(e -> e.getExtendedProperties() != null && e.getExtendedProperties().getShared().containsKey("latitude"))
                .filter(e -> DistanceCalculator.distance(adresseEntity.getLatitude(), adresseEntity.getLongitude(),
                        Double.valueOf(e.getExtendedProperties().getShared().get("latitude")),
                        Double.valueOf(e.getExtendedProperties().getShared().get("longitude")), "K") <= distanceRange)
                .forEach(e -> {
                    final LocalDate date = DateMapper.mapDayToLacalDate(e.getStart().getDateTime());
                    if(result.get(date) == null) {
                        result.put(date, new JourBean(date, EventMapper.INSTANCE.eventToEventOutputBean(e)));
                    } else {
                        result.get(date).getEvents().add(EventMapper.INSTANCE.eventToEventOutputBean(e));
                    }
                });

        return result.values();
    }

    @PostMapping("/rdv")
    public void create(@RequestHeader(value = "uid", required = true) String uid,
                       @RequestHeader(value = "email", required = true) String email,
                       @RequestBody RendezVousInputBean rdv) {

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        /*
            Création de l'évenement dans le calendrier distant
         */
        // TODO gérer la couleur en fonction du status

        final Event event = eventService.createEvent(authInfo,
                EventMapper.INSTANCE.eventInputBeanToEvent(rdv.getEvent()));

        /*
            Création du rdv en local
         */
        if (event != null) {
            saveRendezVous(rdv, event.getICalUID());
        }
    }

    /**
     * Sauvegarde du rendez-vous en local.
     * Un rdv pour chaque appareil, si aucun appareil est précisé, seul le client est précisé.
     */
    private void saveRendezVous(RendezVousInputBean rdv, String eventId) {

        if (rdv.getAppareils().isEmpty()) {

            RendezVousEntity rdvEntity = new RendezVousEntity();
            rdvEntity.setEventId(eventId);

            rdvEntity.setClient(new ClientEntity());
            rdvEntity.getClient().setId(rdv.getClient());

            rdvRepository.save(rdvEntity);

        } else {
            rdv.getAppareils().stream().forEach(a -> {
                /*
                  Création d'un rendez-vous pour chacun des appareils
                 */
                RendezVousEntity rdvEntity = new RendezVousEntity();
                rdvEntity.setEventId(eventId);

                rdvEntity.setAppareil(new AppareilEntity());
                rdvEntity.getAppareil().setId(a);

                rdvEntity.setClient(new ClientEntity());
                rdvEntity.getClient().setId(rdv.getClient());

                rdvRepository.save(rdvEntity);
            });
        }
    }

    /**
     * Construction de l'objet rendez-vous.
     */
    private RendezVousBean buildRendezVousBean(List<RendezVousEntity> rdvEntities, Event event) {

        RendezVousBean rdv = new RendezVousBean();

        if(rdvEntities != null) {
            rdvEntities.stream().forEach(r -> {
                        rdv.getAppareils().add(AppareilMapper.INSTANCE.appareilEntityToAppareilBean(r.getAppareil()));
                        rdv.setClient(ClientMapper.INSTANCE.clientEntityToClientOutpuBean(r.getClient()));
                    }
            );
        }

        rdv.setEvent(EventMapper.INSTANCE.eventToEventOutputBean(event));

        return rdv;
    }
}
