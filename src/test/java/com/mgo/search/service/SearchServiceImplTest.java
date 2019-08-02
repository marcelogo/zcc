package com.mgo.search.service;

import com.mgo.search.converter.PresentationMarshaller;
import com.mgo.search.index.IndexService;
import com.mgo.search.model.User;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchServiceImplTest {

    @Mock
    private IndexService<UserEntity> indexService;
    @Mock
    private PresentationMarshaller<UserEntity, User> marshaller;
    @Mock
    private Repository<UserEntity> repository;
    @Mock
    private UserEntity userEntity;
    @Mock
    private User user;

    private SearchServiceImpl<UserEntity, User> searchService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        searchService = new SearchServiceImpl<>(indexService, repository, marshaller);
    }

    @Test
    void shouldSearchEntityAndReturnPresentationValue() {
        String entityId = "entityId";
        List<String> indexResult = Collections.singletonList(entityId);

        when(indexService.search("name", "john")).thenReturn(indexResult);
        when(repository.byId(entityId)).thenReturn(userEntity);
        when(marshaller.marshall(userEntity)).thenReturn(user);

        Collection<User> search = searchService.search("name", "john");

        assertThat(search, hasSize(1));
        assertThat(search, hasItem(user));
    }

}