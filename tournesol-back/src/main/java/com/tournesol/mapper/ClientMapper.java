package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.AdresseBean;
import com.tournesol.bean.input.ClientInputBean;
import com.tournesol.bean.output.ClientOutputBean;
import com.tournesol.service.entity.AdresseEntity;
import com.tournesol.service.entity.ClientEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper( ClientMapper.class );

    ClientOutputBean clientEntityToClientOutpuBean(ClientEntity clientEntity);

    AdresseBean adresseEntityToAdresseOutputBean(AdresseEntity adresseEntity);

    ClientEntity clientInputeBeanToClientEntity(ClientInputBean clientBean);

    ClientInputBean clientEntityToClientInputBean(ClientEntity clientEntity);
}

