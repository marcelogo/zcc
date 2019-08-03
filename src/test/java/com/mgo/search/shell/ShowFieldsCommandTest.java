package com.mgo.search.shell;

import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchableFieldService;
import com.mgo.search.shell.render.SearchableFieldRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

class ShowFieldsCommandTest {

    @Mock
    private SearchableFieldService searchableFieldService;
    @Mock
    private SearchableFieldRenderer renderer;
    @Mock
    private List<String> fieldsList;

    private ShowFieldsCommand command;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        command = new ShowFieldsCommand(searchableFieldService, renderer);
    }

    @Test
    void shouldRenderFieldsForOrganization(){
        String renderResult = "whatever way ORG fields are going to be renderer";
        when(searchableFieldService.searchableFieldsFor(EntityType.ORGANIZATION)).thenReturn(fieldsList);
        when(renderer.renderEntityAndFields(EntityType.ORGANIZATION.name(), fieldsList)).thenReturn(renderResult);

        assertThat(command.showFields(), containsString(renderResult));
    }

    @Test
    void shouldRenderFieldsForUser(){
        String renderResult = "whatever way USER fields are going to be renderer";
        when(searchableFieldService.searchableFieldsFor(EntityType.USER)).thenReturn(fieldsList);
        when(renderer.renderEntityAndFields(EntityType.USER.name(), fieldsList)).thenReturn(renderResult);

        assertThat(command.showFields(), containsString(renderResult));
    }

    @Test
    void shouldRenderFieldsForTicket(){
        String renderResult = "whatever way TICKET fields are going to be renderer";
        when(searchableFieldService.searchableFieldsFor(EntityType.TICKET)).thenReturn(fieldsList);
        when(renderer.renderEntityAndFields(EntityType.TICKET.name(), fieldsList)).thenReturn(renderResult);

        assertThat(command.showFields(), containsString(renderResult));
    }

}