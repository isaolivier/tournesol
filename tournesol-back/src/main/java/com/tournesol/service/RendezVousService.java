package com.tournesol.service;

import com.tournesol.bean.input.RendezVousInputBean;
import com.tournesol.service.entity.AppareilEntity;
import com.tournesol.service.entity.ClientEntity;
import com.tournesol.service.entity.RendezVousEntity;
import com.tournesol.service.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RendezVousService {

    @Autowired
    RendezVousRepository rdvRepository;

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

    public Map<String, List<RendezVousEntity>> findRendezVousGroupByEvent(Collection<String> eventIds) {

        return rdvRepository.findRendezVousEntities(eventIds).stream()
                .collect(Collectors.groupingBy(RendezVousEntity::getEventId, Collectors.toList()));
    }
}
