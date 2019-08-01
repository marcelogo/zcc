package com.mgo.search.reposiory;

import com.mgo.search.reposiory.entity.Entity;

import java.util.Collection;

public interface Repository<T extends Entity> {

    T byId(String id);

    Collection<T> findAll();
}
