package com.mgo.search.index;

import com.mgo.search.reposiory.entity.Entity;

import java.util.Collection;
import java.util.List;

public interface IndexService<T extends Entity> {

    Collection<String> search(String field, String word);

    List<String> indexableFieldsFor(Class<T> clazz);

}
