package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.EntrepriseBean;
import com.tournesol.service.repository.entity.EntrepriseEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntrepriseMapper {

    EntrepriseMapper INSTANCE = Mappers.getMapper( EntrepriseMapper.class );

    EntrepriseBean entrepriseToEntrepriseBean(EntrepriseEntity clientEntity);
    EntrepriseEntity entrepriseBeanToEntreprise(EntrepriseBean clientBean);

}

