package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.AppareilBean;
import com.tournesol.service.entity.AppareilEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppareilMapper {

    AppareilMapper INSTANCE = Mappers.getMapper( AppareilMapper.class );

    AppareilBean appareilEntityToAppareilBean(AppareilEntity appareilEntity);

    AppareilEntity appareilBeanToAppareilEntity(AppareilBean appareilBean);
}

