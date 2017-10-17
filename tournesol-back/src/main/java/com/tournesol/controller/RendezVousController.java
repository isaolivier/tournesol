package com.tournesol.controller;

import com.google.api.services.calendar.model.Event;
import com.google.maps.model.DistanceMatrix;
import com.tournesol.bean.AuthInfo;
import com.tournesol.bean.DistanceRendezVousBean;
import com.tournesol.bean.JourBean;
import com.tournesol.bean.RendezVousBean;
import com.tournesol.bean.input.RendezVousInputBean;
import com.tournesol.bean.output.EventOutputBean;
import com.tournesol.config.EntrepriseConfiguration;
import com.tournesol.mapper.AppareilMapper;
import com.tournesol.mapper.ClientMapper;
import com.tournesol.mapper.DateMapper;
import com.tournesol.mapper.EventMapper;
import com.tournesol.service.AdresseService;
import com.tournesol.service.FlyDistanceService;
import com.tournesol.service.RendezVousService;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.RendezVousEntity;
import com.tournesol.service.google.DistanceService;
import com.tournesol.service.google.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private RendezVousService rendezVousService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AdresseService adresseService;

    @Autowired
    private FlyDistanceService flyDistanceService;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private EntrepriseConfiguration entrepriseConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(RendezVousController.class);

    /**
     * Recherche des distances et temps de parcours entre les rdvs d'une journée donnée.
     */
    @GetMapping("/rdv/distance")
    public List<DistanceRendezVousBean> searchDistanceAndTime(@RequestHeader(value = "uid", required = true) String uid,
                                                              @RequestHeader(value = "email", required = true) String email,
                                                              @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<DistanceRendezVousBean> result = new ArrayList<>();

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        LocalDate dateRecherche = date == null ? LocalDate.now() : date;

        final List<EventOutputBean> events = eventService.getEvents(authInfo, dateRecherche).stream()
                .map(e -> EventMapper.INSTANCE.eventToEventOutputBean(e))
                .sorted(Comparator.comparing(EventOutputBean::getStart))
                .collect(Collectors.toList());


        IntStream.range(0, events.size() - 1).forEach(i -> {

            final EventOutputBean currentEvent = events.get(i);
            final EventOutputBean nextEvent = events.get(i + 1);

            final DistanceMatrix timeDistance = distanceService.getDurationAndDistance(currentEvent.getEnd(), currentEvent.getCoordonnees(), nextEvent.getCoordonnees());

            String distance = timeDistance.rows[0].elements[0].distance.humanReadable;
            String duration = timeDistance.rows[0].elements[0].duration.humanReadable;
            //String durationInTrafic = timeDistance.rows[0].elements[0].durationInTraffic.humanReadable;

            result.add(new DistanceRendezVousBean(currentEvent.getId(), nextEvent.getId(), distance, duration));
        });

        return result;
    }

    /**
     * Recherche de l'ensemble des rendez-vous sur une journée donnée.
     *
     * @param date, date de recherche des rendez-vous, si celle-ci n'est pas précisée c'est la date du jour qui sera utilisée.
     * @return la liste des rendez-vous du jour, aggrégeant l'ensemble des infos relatives au rdv (client, appareil ...)
     */
    @GetMapping("/rdvs")
    public List<RendezVousBean> greeting(@RequestHeader(value = "uid", required = true) String uid,
                                         @RequestHeader(value = "email", required = true) String email,
                                         @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        /*
            Recherche des évenements dans le calendrier distant (google)
         */
        LocalDate dateRecherche = date == null ? LocalDate.now() : date;

        final Map<String, Event> eventMap = eventService.getEvents(authInfo, dateRecherche).stream()
                .collect(Collectors.toMap(Event::getICalUID, e -> e));

        /*
            Recherche des rdvs dans la base locale,
            Regroupement par eventId.
         */
        final Map<String, List<RendezVousEntity>> rendezVousEntities = rendezVousService.findRendezVousGroupByEvent(new ArrayList<>(eventMap.keySet()));

        /*
            Aggrégation des données, et tri sur la date de de début
         */
        return eventMap.entrySet().stream()
                .map(e -> buildRendezVousBean(rendezVousEntities.get(e.getKey()), e.getValue()))
                .sorted(Comparator.comparing(r -> r.getEvent().getStart()))
                .collect(Collectors.toList());
    }



    /**
     * Recherche des dates optimales par rapport à un client donné.
     * Lorsque le temps de rendez-vous est fourni, on filtre sur les journées qui disposent d'un créneau disponible pour ce temps
     *
     * @param dayRange      Nombre de jours sur lesquels on effectue une recherche des rdvs existants.
     * @param distanceRange Distance en max en km entre l'adresse et les adresses recharchées.
     * @param adresseId     Identifiant de l'adresse à partir de laquelle effectuer une recherche.
     * @param rdvSize       Temps prévu du rendez-vous en minutes.
     * @return la liste des rendez-vous du jour, aggrégeant l'ensemble des infos relatives au rdv (client, appareil ...)
     */
    @GetMapping("/rdv/search")
    public Collection<JourBean> search(@RequestHeader(value = "uid", required = true) String uid,
                                       @RequestHeader(value = "email", required = true) String email,
                                       @RequestParam(value = "dayRange", required = true) int dayRange,
                                       @RequestParam(value = "distanceRange", required = true) int distanceRange,
                                       @RequestParam(value = "placeId", required = true) String placeId,
                                       @RequestParam(value = "adresseId", required = false) Long adresseId,
                                       @RequestParam(value= "rdvSize", required = false) Integer rdvSize) throws Exception {

        List<JourBean> result = new ArrayList<>();

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        AdresseEntity adresseEntity = adresseService.findAdresseDetails(placeId, adresseId);

        final Double latitude = adresseEntity.getLatitude();
        final Double longitude = adresseEntity.getLongitude();

        final Map<LocalDate, JourBean> dayMap = new HashMap<>();

        /*
            Recherche des évenements dans le calendrier distant (google)
         */
        final List<Event> events = eventService.getEvents(authInfo, LocalDate.now(), dayRange);

        events.stream()
                .filter(e -> flyDistanceService.calculateFlyDistance(latitude, longitude, e) <= distanceRange)
                .forEach(e -> {
                    final LocalDate date = DateMapper.mapDayToLacalDate(e.getStart().getDateTime());
                    if (dayMap.get(date) == null) {
                        dayMap.put(date, new JourBean(date, EventMapper.INSTANCE.eventToEventOutputBean(e)));
                    } else {
                        dayMap.get(date).getEvents().add(EventMapper.INSTANCE.eventToEventOutputBean(e));
                    }
                });

        result.addAll(dayMap.values());

        /*
            Filtrage des journées possédant un créneau de n (rdvSize) minutes disponbiles
         */
        if(rdvSize != null) {
            final LocalTime heureOuverture = entrepriseConfiguration.getHeureOuverture();
            final LocalTime heureFermeture = entrepriseConfiguration.getHeureFermeture();

            result = result.stream()
                    .filter(j -> j.dayContainsFreePeriod(rdvSize, heureOuverture, heureFermeture))
                    .collect(Collectors.toList());
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retourne la liste des dates de jours ouverts, ne contenant aucun evenement.
     */
    @GetMapping("/rdv/free")
    public Collection<LocalDate> search(@RequestHeader(value = "uid", required = true) String uid,
                                       @RequestHeader(value = "email", required = true) String email,
                                       @RequestParam(value = "startDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @RequestParam(value = "endDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws Exception {

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        /*
            Recherche des évenements dans le calendrier distant (google)
         */
        final Set<LocalDate> bookedDates = eventService.getEvents(authInfo, startDate, endDate).stream()
                .map(e -> EventMapper.INSTANCE.eventDateTimeToLocalDateTime(e.getStart()).toLocalDate())
                .collect(Collectors.toSet());

        /*
            Filtrage sur l'ensemble des dates entre startDate et endDate
         */
        return IntStream.range(0, (int) ChronoUnit.DAYS.between(startDate, endDate))
                .mapToObj(startDate::plusDays)
                .filter(d -> entrepriseConfiguration.getJoursOuverture().contains(d.getDayOfWeek()))
                .filter(d -> !bookedDates.contains(d))
                .collect(Collectors.toList());

    }

    @PostMapping("/rdv")
    public void create(@RequestHeader(value = "uid", required = true) String uid,
                       @RequestHeader(value = "email", required = true) String email,
                       @RequestBody RendezVousInputBean rdv) throws Exception {

        AuthInfo authInfo = new AuthInfo(null, email, uid);

        final AdresseEntity adresseEntity = adresseService.findAdresseDetails(rdv.getPlaceId(), rdv.getAdresseId());

        /*
            Création de l'évenement dans le calendrier distant
         */
        Event event = EventMapper.INSTANCE.eventInputBeanToEvent(rdv.getEvent());

        EventMapper.INSTANCE.completeLatitudeLongitude(event, adresseEntity.getLatitude(), adresseEntity.getLongitude());

        event = eventService.createEvent(authInfo, event);

        /*
            Création du rdv en local
         */
        if (event != null) {
            rendezVousService.saveRendezVous(rdv, event.getICalUID());
        }
    }

    /**
     * Construction de l'objet rendez-vous.
     */
    private RendezVousBean buildRendezVousBean(List<RendezVousEntity> rdvEntities, Event event) {

        RendezVousBean rdv = new RendezVousBean();

        if (rdvEntities != null) {
            rdvEntities.forEach(r -> {
                        rdv.getAppareils().add(AppareilMapper.INSTANCE.appareilEntityToAppareilBean(r.getAppareil()));
                        rdv.setClient(ClientMapper.INSTANCE.clientEntityToClientOutpuBean(r.getClient()));
                    }
            );
        }

        rdv.setEvent(EventMapper.INSTANCE.eventToEventOutputBean(event));

        return rdv;
    }
}
