package com.mgo.search.converter;

import com.mgo.search.model.PresentationDto;
import com.mgo.search.reposiory.entity.Entity;

public interface PresentationMarshaller<E extends Entity, P extends PresentationDto> {

    P marshall(E entity);

}
