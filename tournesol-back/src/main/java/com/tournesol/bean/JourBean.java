package com.tournesol.bean;

import com.google.common.collect.Lists;
import com.tournesol.bean.output.EventOutputBean;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Propostions de rendez-vous pour une date donnée.
 * Les events sont les évènements déjà programmés lors de cette journée.
 */
public class JourBean {

    private LocalDate date;

    private List<EventOutputBean> events;

    public JourBean(LocalDate date, EventOutputBean event) {
        this.date = date;
        this.events = Lists.newArrayList(event);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<EventOutputBean> getEvents() {
        return events;
    }

    public void setEvents(List<EventOutputBean> events) {
        this.events = events;
    }

    /**
     * Retourne true lorsque la journée contient au moins un créneau de n (rdvSize) minutes libres.
     * @param rdvSize temps disponible en minutes recherché.
     * @param heureOuverture heure d'ouverture de l'etablissement.
     * @param heureFermeture heure de de fermeture de l'éatablissement.
     */
    public boolean dayContainsFreePeriod(Integer rdvSize, LocalTime heureOuverture, LocalTime heureFermeture) {

        final LocalDateTime ouverture = LocalDateTime.of(date, heureOuverture);
        final LocalDateTime fermeture = LocalDateTime.of(date, heureFermeture);

        if(events.isEmpty() || hasFreeTime(rdvSize, ouverture, events.get(0).getStart())
                || hasFreeTime(rdvSize, getEvents().get(getEvents().size() - 1).getEnd(), fermeture)) {
            return true;

        } else {

            for (int i = 0; i < events.size() - 1; i++) {
                if (hasFreeTime(rdvSize, events.get(i).getEnd(), events.get(i + 1).getStart())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasFreeTime(Integer rdvSize, LocalDateTime date1, LocalDateTime date2) {
        return Duration.between(date1, date2).toMinutes() >= rdvSize;
    }
}
