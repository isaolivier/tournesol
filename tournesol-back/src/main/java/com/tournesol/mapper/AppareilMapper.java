package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.AppareilBean;
import com.tournesol.service.repository.entity.AppareilEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppareilMapper {

    AppareilMapper INSTANCE = Mappers.getMapper( AppareilMapper.class );

    AppareilBean appareilToAppareilBean(AppareilEntity clientEntity);
    AppareilEntity appareilBeanToAppareil(AppareilBean clientBean);

}

