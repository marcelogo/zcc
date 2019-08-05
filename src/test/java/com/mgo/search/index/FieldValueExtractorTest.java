package com.mgo.search.index;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FieldValueExtractorTest {

    private static final String ENTITY_ID = "entityId";
    private static final long LONG_FIELD_VALUE = 9239121739217392L;
    private static final int INT_FIELD_VALUE = 135;
    private static final String STRING_FIELD_VALUE = "stringField";
    private Object mockField = null;
    private IndexableMockEntity entity;

    private FieldValueExtractor extractor = new FieldValueExtractor();

    @BeforeEach
    void setUp(){
        entity = new IndexableMockEntity();
        entity.setId(ENTITY_ID);
        entity.setLongField(LONG_FIELD_VALUE);
        entity.setIntField(INT_FIELD_VALUE);
        entity.setStringField(STRING_FIELD_VALUE);
        entity.setBooleanField(Boolean.TRUE);
        entity.setCollectionField(Arrays.asList(STRING_FIELD_VALUE, LONG_FIELD_VALUE, INT_FIELD_VALUE, Boolean.TRUE));
        entity.setNullableField(null);
    }

    @Test
    void shouldExtractStringPropertyValue() throws NoSuchFieldException {
        Field stringField = entity.getClass().getDeclaredField("stringField");

        assertThat(extractor.valueAsString(entity, stringField), is(STRING_FIELD_VALUE));
    }

    @Test
    void shouldExtractLongPropertyValue() throws NoSuchFieldException {
        Field longField = entity.getClass().getDeclaredField("longField");

        assertThat(extractor.valueAsString(entity, longField), is(String.valueOf(LONG_FIELD_VALUE)));
    }

    @Test
    void shouldExtractIntegerPropertyValue() throws NoSuchFieldException {
        Field intField = entity.getClass().getDeclaredField("intField");

        assertThat(extractor.valueAsString(entity, intField), is(String.valueOf(INT_FIELD_VALUE)));
    }

    @Test
    void shouldReturnTrueAsStringIfBooleanFieldIsTrue() throws NoSuchFieldException {
        Field booleanField = entity.getClass().getDeclaredField("booleanField");

        assertThat(extractor.valueAsString(entity, booleanField), is("true"));
    }

    @Test
    void shouldReturnFalseAsStringIfBooleanFieldIsFalse() throws NoSuchFieldException {
        entity.setBooleanField(Boolean.FALSE);
        Field booleanField = entity.getClass().getDeclaredField("booleanField");

        assertThat(extractor.valueAsString(entity, booleanField), is("false"));
    }

    @Test
    void shouldExtractCollectionPropertyValueAsConcatenatedString() throws NoSuchFieldException {
        Field collectionField = entity.getClass().getDeclaredField("collectionField");

        String expectedResult = StringUtils.join(Arrays.asList(STRING_FIELD_VALUE,
                String.valueOf(LONG_FIELD_VALUE), String.valueOf(INT_FIELD_VALUE), String.valueOf(Boolean.TRUE)), " ");


        assertThat(extractor.valueAsString(entity, collectionField), is(expectedResult));
    }

    @Test
    void shouldReturnEmptyStringIfFieldValueIsNull() throws NoSuchFieldException {
        Field nullableField = entity.getClass().getDeclaredField("nullableField");

        assertThat(extractor.valueAsString(entity, nullableField), is(StringUtils.EMPTY));
    }

    @Test
    void shouldReturnEmptyStringIfFieldValueCantBeAccessed() throws NoSuchFieldException {
        Field mockField = this.getClass().getDeclaredField("mockField");

        assertThat(extractor.valueAsString(entity, mockField), is(StringUtils.EMPTY));
    }

}