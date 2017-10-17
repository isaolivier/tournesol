package com.tournesol.mapper;

import com.tournesol.bean.EntrepriseBean;
import com.tournesol.service.entity.EntrepriseEntity;

import java.time.DayOfWeek;

/**
 * Created by iolivier on 23/09/2017.
 */
public abstract class EntrepriseMapperDecorator implements EntrepriseMapper {

    private final EntrepriseMapper delegate;

    public EntrepriseMapperDecorator(EntrepriseMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public EntrepriseBean map(EntrepriseEntity entrepriseEntity) {

        EntrepriseBean entreprise = delegate.map(entrepriseEntity);

        final Integer[] days = entrepriseEntity.getListJoursOuverture().stream().map(DayOfWeek::getValue).toArray(Integer[]::new);
        entreprise.getConfiguration().setJoursOuverture(days);

        return entreprise;
    }
}
