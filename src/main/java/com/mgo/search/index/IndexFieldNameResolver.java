package com.mgo.search.index;

import java.lang.reflect.Field;

public interface IndexFieldNameResolver {

    String fieldNameFor(Field field);

}
