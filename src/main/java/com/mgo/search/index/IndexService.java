package com.mgo.search.index;

import com.mgo.search.reposiory.entity.Entity;

import java.util.Collection;

public interface IndexService<T extends Entity> {

    //TODO strings for ids start to smell bad! Maybe extract and make it generic????
    Collection<String> search(String field, String pattern);
}
