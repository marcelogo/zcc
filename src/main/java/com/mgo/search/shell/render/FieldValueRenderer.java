package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.PresentationDto;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import jdk.internal.joptsimple.internal.Strings;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

public class FieldValueRenderer {

    private static final String DEFAULT_VALUE = Strings.EMPTY;
    private static final String STRING_SEPARATOR = " ";

    String render(PresentationDto dto, Field field) {
        field.setAccessible(true);
        return field.getName() + ": " + fieldValueAsString(getFieldValue(dto, field));
    }

    private String fieldValueAsString(Object fieldValue) {
        if (fieldValue == null) {
            return DEFAULT_VALUE;
        } else if (Collection.class.isAssignableFrom(fieldValue.getClass())) {
            Collection<?> collectionFieldValue = (Collection) fieldValue;
            return collectionFieldValue.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(STRING_SEPARATOR));
        } else if (Organization.class.isAssignableFrom(fieldValue.getClass())) {
            return ((Organization) fieldValue).getName();
        } else if (User.class.isAssignableFrom(fieldValue.getClass())) {
            return ((User) fieldValue).getName();
        } else if (Ticket.class.isAssignableFrom(fieldValue.getClass())) {
            return ((Ticket) fieldValue).getSubject();
        }

        return String.valueOf(fieldValue);
    }

    private Object getFieldValue(PresentationDto dto, Field field) {
        try {
            return field.get(dto);
        } catch (Exception e) {
            return DEFAULT_VALUE;
        }
    }
}
