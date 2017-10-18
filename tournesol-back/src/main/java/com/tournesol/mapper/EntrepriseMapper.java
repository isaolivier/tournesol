package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.EntrepriseBean;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.EntrepriseEntity;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(EntrepriseMapperDecorator.class)
public interface EntrepriseMapper {

    EntrepriseMapper INSTANCE = Mappers.getMapper( EntrepriseMapper.class );

    @Mappings({
            @Mapping(target = "configuration.heureOuverture", source = "heureOuverture"),
            @Mapping(target = "configuration.heureFermeture", source = "heureFermeture"),
            @Mapping(target = "configuration.timeStep", source = "timeStep"),
            @Mapping(target = "configuration.tempsRdv", source = "tempsRdv"),
            @Mapping(target = "configuration.searchDays", source = "searchDays"),
            @Mapping(target = "configuration.searchDistance", source = "searchDistance")
    })
    EntrepriseBean map(EntrepriseEntity entrepriseEntity);

    EntrepriseEntity map(EntrepriseBean entrepriseBean);

    AdresseBean map(AdresseEntity adresseEntity);

    AdresseEntity map(AdresseBean adresseBean);
}

