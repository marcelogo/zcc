package com.mgo.search.service;

import com.mgo.search.index.IndexFieldNameResolver;
import com.mgo.search.reposiory.entity.Entity;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class SearchableFieldServiceTest {

    @Mock
    private IndexFieldNameResolver fieldNameResolver;

    private SearchableFieldService searchableFieldService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        setUpDumbImplementationForFieldNameResolver();

        searchableFieldService = new SearchableFieldService(fieldNameResolver);
    }

    private void setUpDumbImplementationForFieldNameResolver() {
        when(fieldNameResolver.fieldNameFor(any(Field.class))).thenAnswer(invocation -> {
            Field field  = (Field) invocation.getArguments()[0];
            return field.getName();
        });
    }

    @Test
    void shouldRecoverFieldsForOrganizationEntityWhenEntityTypeIsOrganization(){
        List<String> fieldNames = searchableFieldService.searchableFieldsFor(EntityType.ORGANIZATION);

        assertThat(fieldNames, hasSize(OrganizationEntity.class.getDeclaredFields().length));
        assertThat(fieldNames, contains(fieldNamesForEntity(OrganizationEntity.class)));
    }

    @Test
    void shouldRecoverFieldsForUserEntityWhenEntityTypeIsUser(){
        List<String> fieldNames = searchableFieldService.searchableFieldsFor(EntityType.USER);

        assertThat(fieldNames, hasSize(UserEntity.class.getDeclaredFields().length));
        assertThat(fieldNames, contains(fieldNamesForEntity(UserEntity.class)));
    }

    @Test
    void shouldRecoverFieldsForUserEntityWhenEntityTypeIsTicket(){
        List<String> fieldNames = searchableFieldService.searchableFieldsFor(EntityType.TICKET);

        assertThat(fieldNames, hasSize(TicketEntity.class.getDeclaredFields().length));
        assertThat(fieldNames, contains(fieldNamesForEntity(TicketEntity.class)));
    }

    private String[] fieldNamesForEntity(Class<? extends Entity> clazz){
        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()).toArray(new String[]{});
    }

}