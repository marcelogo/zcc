package com.mgo.search.service;

import com.mgo.search.model.PresentationDto;

import java.util.Collection;

public interface SearchService<P extends PresentationDto> {

    Collection<P> search(String field, String word);

}
