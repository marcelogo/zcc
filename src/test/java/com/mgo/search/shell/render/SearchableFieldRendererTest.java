package com.mgo.search.shell.render;

import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchableFieldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SearchableFieldRendererTest {

    @Mock
    private SearchableFieldService searchableFieldService;

    private SearchableFieldRenderer renderer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(searchableFieldService.searchableFieldsFor(any(EntityType.class))).thenReturn(Arrays.asList("field1", "field2"));

        renderer = new SearchableFieldRenderer(searchableFieldService);
    }

    @ParameterizedTest
    @EnumSource(EntityType.class)
    void shouldCreateStringWithEntityNameFollowedByFieldNamesSeparatedByNewLine(EntityType entityType) {
        String actual = renderer.renderForType(entityType);

        String expected = entityType.name() +
                TestConstants.HORIZONTAL_BAR +
                "    field1" +
                TestConstants.LINE_SEPARATOR +
                "    field2";

        assertThat(actual, is(expected));

    }

}