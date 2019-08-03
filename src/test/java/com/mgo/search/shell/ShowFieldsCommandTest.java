package com.mgo.search.shell;

import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchableFieldService;
import com.mgo.search.shell.render.SearchableFieldRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

class ShowFieldsCommandTest {

    @Mock
    private SearchableFieldRenderer renderer;

    private ShowFieldsCommand command;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        command = new ShowFieldsCommand(renderer);
    }

    @ParameterizedTest
    @EnumSource(EntityType.class)
    void shouldRenderFieldsForAllEntityTypes(EntityType entityType){
        String renderResult = "whatever way fields are going to be renderer";
        when(renderer.renderForType(entityType)).thenReturn(renderResult);

        assertThat(command.showFields(), containsString(renderResult));
    }

}