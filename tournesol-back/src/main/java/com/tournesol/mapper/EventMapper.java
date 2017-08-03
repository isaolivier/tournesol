package com.tournesol.mapper;

/**
 * Created by draluy on 28/06/2017.
 */


import com.tournesol.bean.EventBean;
import com.tournesol.service.entity.EventEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );

    EventBean eventToEventBean(EventEntity eventEntity);
    EventEntity eventBeanToEvent(EventBean eventBean);

}

