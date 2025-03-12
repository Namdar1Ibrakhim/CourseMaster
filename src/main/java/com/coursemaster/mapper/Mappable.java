package com.coursemaster.mapper;

import java.util.List;

public interface Mappable<ENTITY, REQUEST, RESPONSE> {

    RESPONSE toDto(ENTITY entity);

    List<RESPONSE> toDto(List<ENTITY> entities);

    ENTITY toEntity(REQUEST request);

    List<ENTITY> toEntity(List<REQUEST> requests);

}