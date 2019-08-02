package com.mgo.search.service;

import com.mgo.search.converter.PresentationMarshaller;
import com.mgo.search.index.IndexService;
import com.mgo.search.model.PresentationDto;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.Entity;

import java.util.Collection;
import java.util.stream.Collectors;

public class SearchServiceImpl<E extends Entity, P extends PresentationDto> implements SearchService<P> {

    private IndexService<E> indexService;
    private Repository<E> repository;
    private PresentationMarshaller<E, P> marshaller;

    public SearchServiceImpl(IndexService<E> indexService, Repository<E> repository, PresentationMarshaller<E, P> marshaller){
        this.indexService = indexService;
        this.repository = repository;
        this.marshaller = marshaller;
    }

    @Override
    public Collection<P> search(String field, String pattern) {
        return indexService.search(field, pattern).stream()
                .map(repository::byId)
                .map(marshaller::marshall)
                .collect(Collectors.toList());
    }
}
