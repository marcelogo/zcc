package com.mgo.search.index;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public class SerializedFieldNameResolverImpl implements IndexFieldNameResolver {

    public String fieldNameFor(Field field) {
        SerializedName serializedName = field.getAnnotation(SerializedName.class);
        return serializedName != null ? serializedName.value() : field.getName();
    }

}
