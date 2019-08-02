package com.mgo.search.index;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SerializedFieldNameResolverImplTest {

    private SerializedFieldNameResolverImpl fieldNameResolver = new SerializedFieldNameResolverImpl();

    @Test
    void shouldReturnSerializedNameWhenAnnotationIsPresent() throws NoSuchFieldException {
        Field field = IndexableMockEntity.class.getDeclaredField("fieldWithSerializableName");

        assertThat(fieldNameResolver.fieldNameFor(field), is("_serialized_name"));
    }

    @Test
    void shouldReturnActualFieldNameWhenAnnotationIsAbsent() throws NoSuchFieldException {
        Field field = IndexableMockEntity.class.getDeclaredField("stringField");

        assertThat(fieldNameResolver.fieldNameFor(field), is("stringField"));
    }

}