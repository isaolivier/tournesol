package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.EventBean;
import com.tournesol.service.entity.EventEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventBeanMapper {

    EventBeanMapper INSTANCE = Mappers.getMapper( EventBeanMapper.class );

    EventBean eventEntityToEventBean(EventEntity eventEntity);
    EventEntity eventBeanToEventEntity(EventBean eventBean);
}

