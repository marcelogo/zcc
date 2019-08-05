package com.mgo.search.index;

import com.mgo.search.reposiory.entity.Entity;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

public class FieldValueExtractor {

    private static final String DEFAULT_VALUE = StringUtils.EMPTY;
    private static final String STRING_SEPARATOR = " ";

    public String valueAsString(Entity entity, Field field){
        field.setAccessible(true);
        return fieldValueAsString(getFieldValue(entity, field));
    }

    private String fieldValueAsString(Object fieldValue) {
        if (fieldValue == null) {
            return DEFAULT_VALUE;
        } else if (Collection.class.isAssignableFrom(fieldValue.getClass())){
            Collection<?> collectionFieldValue = (Collection) fieldValue;
            return collectionFieldValue.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(STRING_SEPARATOR));
        }

        return String.valueOf(fieldValue);
    }

    private Object getFieldValue(Entity entity, Field field) {
        try {
            return field.get(entity);
        } catch (Exception e) {
            return DEFAULT_VALUE;
        }
    }
}
