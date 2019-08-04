package com.mgo.search.shell;

import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchService;
import com.mgo.search.shell.render.PresentationDtoRendererResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchCommandTest {

    private static final String SEARCH_FIELD = "searchField";
    private static final String SEARCH_VALUE = "searchValue";

    @Mock
    private Map<EntityType, SearchService> searchServiceMap;
    @Mock
    private PresentationDtoRendererResolver rendererResolver;
    @Mock
    private SearchService<SimpleMockDto> searchService;

    private SearchCommand searchCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(searchServiceMap.get(any(EntityType.class))).thenReturn(searchService);

        searchCommand = new SearchCommand(searchServiceMap, rendererResolver);
    }

    @ParameterizedTest
    @ValueSource(strings = { "orgaNization", "user", "Ticket" })
    void shouldReturnRenderedSearchResults(String entity) {
        SimpleMockDto result1 = mock(SimpleMockDto.class);
        SimpleMockDto result2 = mock(SimpleMockDto.class);

        when(searchService.search(SEARCH_FIELD, SEARCH_VALUE)).thenReturn(Arrays.asList(result1, result2));
        String renderedResult1 = "rendered result 1";
        when(rendererResolver.render(result1)).thenReturn(renderedResult1);
        String renderedResult2 = "rendered result 2";
        when(rendererResolver.render(result2)).thenReturn(renderedResult2);

        String renderedResults = searchCommand.search(entity, SEARCH_FIELD, SEARCH_VALUE);

        String expected = renderedResult1 + System.lineSeparator() + System.lineSeparator() + renderedResult2;

        assertThat(renderedResults, is(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = { "orgaNization", "user", "Ticket" })
    void shouldReturnNoResultsFoundWhenSearchResultIsEmpty(String entity) {
        String absentSearchValue = "I'm not there";
        when(searchService.search(SEARCH_FIELD, absentSearchValue)).thenReturn(Collections.emptyList());

        assertThat(searchCommand.search(entity, SEARCH_FIELD, absentSearchValue), is("No results found for criteria."));
    }

    @Test
    void shouldReturnErrorMessageWhenEntityIsNotSupported() {
        String errorMessage = "Invalid entity [wally]. Possible values are [Organization | User | Ticket]";
        assertThat(searchCommand.search("wally", SEARCH_FIELD, SEARCH_VALUE), is(errorMessage));
    }
}