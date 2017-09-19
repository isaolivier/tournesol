package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.ClientOutputBean;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientOutputMapper {

    ClientOutputMapper INSTANCE = Mappers.getMapper( ClientOutputMapper.class );

    ClientOutputBean map(ClientEntity clientEntity);

    AdresseBean map(AdresseEntity adresseEntity);
}

