package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.ClientBean;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper( ClientMapper.class );

    ClientBean map(ClientEntity clientEntity);
    ClientEntity map(ClientBean clientBean);

    AdresseBean map(AdresseEntity value);
    AdresseEntity map(AdresseBean value);
}

