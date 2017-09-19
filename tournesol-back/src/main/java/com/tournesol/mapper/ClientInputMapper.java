package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.input.ClientInputBean;
import com.tournesol.service.entity.ClientEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientInputMapper {

    ClientInputMapper INSTANCE = Mappers.getMapper( ClientInputMapper.class );

    ClientEntity map(ClientInputBean clientBean);

    ClientInputBean map(ClientEntity clientEntity);

}

