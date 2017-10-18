package com.tournesol.service;

import com.tournesol.bean.input.RendezVousInputBean;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.entity.RendezVousEntity;
import com.tournesol.service.repository.RendezVousRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RendezVousService {

    RendezVousRepository rdvRepository;

    @Autowired
    public RendezVousService(RendezVousRepository rdvRepository) {
        this.rdvRepository = rdvRepository;
    }

    /**
     * Sauvegarde du rendez-vous en local.
     * Un rdv pour chaque appareil, si aucun appareil est précisé, seul le client est précisé.
     */
    public void saveRendezVous(RendezVousInputBean rdv, String eventId) {

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

    public Map<String, List<RendezVousEntity>> findRendezVousGroupByEvent(Collection<String> eventIds, boolean fullRdv) {

        List<RendezVousEntity> rendezVousEntities;

        if (fullRdv) {
            rendezVousEntities = rdvRepository.findRendezVousEntities(eventIds);
        } else {
            rendezVousEntities = rdvRepository.findNakedRendezVousEntities(eventIds);
        }

        return rendezVousEntities.stream()
                .collect(Collectors.groupingBy(RendezVousEntity::getEventId, Collectors.toList()));
    }

}
