package com.mgo.search.index;

import com.mgo.search.reposiory.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;


class InMemoryMapIndexServiceTest {

    private static final String ENTITY_ID = UUID.randomUUID().toString();
    private static final String ENTITY2_ID = UUID.randomUUID().toString();
    private static final String ENTITY3_ID = UUID.randomUUID().toString();
    private static final String FIELD_VALUE_OF_SINGLE_ENTITY = "word 123 true";
    private static final String COMMON_FIELD_VALUE_OF_TWO_ENTITIES = "two trees";

    private IndexableMockEntity entity = new IndexableMockEntity(ENTITY_ID);
    private IndexableMockEntity entity2 = new IndexableMockEntity(ENTITY2_ID);
    private IndexableMockEntity entity3 = new IndexableMockEntity(ENTITY3_ID);

    @Mock
    private Repository<IndexableMockEntity> repository;
    @Mock
    private FieldValueExtractor fieldValueExtractor;

    private InMemoryMapIndexService<IndexableMockEntity> indexService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        when(repository.findAll()).thenReturn(Arrays.asList(entity, entity2, entity3));
        when(fieldValueExtractor.valueAsString(eq(entity), any(Field.class))).thenReturn(FIELD_VALUE_OF_SINGLE_ENTITY);
        when(fieldValueExtractor.valueAsString(eq(entity2), any(Field.class))).thenReturn("entity2");
        when(fieldValueExtractor.valueAsString(eq(entity3), any(Field.class))).thenReturn("entity3");

        indexService = new InMemoryMapIndexService<>(repository, fieldValueExtractor);
    }

    @Test
    void shouldReturnEntityIdWhenSearchPatternMatchesCompletelyAnyFieldToken() {
        indexService.index();

        Collection<String> ids = indexService.search("stringField", "word");
        assertThat(ids, hasSize(1));
        assertThat(ids, hasItem(ENTITY_ID));
    }

    @Test
    void shouldReturnTwoEntityIdsWhenSearchPatternMatchesCompletelyAnyFieldTokenIn2Entities() {
        when(fieldValueExtractor.valueAsString(eq(entity2), any(Field.class))).thenReturn(COMMON_FIELD_VALUE_OF_TWO_ENTITIES);
        when(fieldValueExtractor.valueAsString(eq(entity3), any(Field.class))).thenReturn(COMMON_FIELD_VALUE_OF_TWO_ENTITIES);

        indexService.index();

        Collection<String> ids = indexService.search("stringField", "trees");
        assertThat(ids, hasSize(2));
        assertThat(ids, hasItems(ENTITY2_ID, ENTITY3_ID));
    }

    @Test
    void shouldBeAbleToSearchFieldBySerializedName(){
        when(fieldValueExtractor.valueAsString(eq(entity3), any(Field.class))).thenReturn("search me with my other name");

        indexService.index();

        Collection<String> ids = indexService.search("_serialized_name", "other");
        assertThat(ids, hasSize(1));
        assertThat(ids, hasItems(ENTITY3_ID));
    }

}