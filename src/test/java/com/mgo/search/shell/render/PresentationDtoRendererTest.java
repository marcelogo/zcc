package com.mgo.search.shell.render;

import com.mgo.search.shell.SimpleMockDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class PresentationDtoRendererTest {

    private static final int ID = 987654321;
    private static final String NAME = "I'm a little mock";

    private SimpleMockDto simpleMockDto =  new SimpleMockDto(ID, NAME);

    @Mock
    private FieldValueRenderer fieldValueRenderer;

    private PresentationDtoRenderer renderer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        renderer = new PresentationDtoRenderer(fieldValueRenderer);
    }

    @Test
    void shouldRenderPresentationDtoAsClassNameAndFieldAndFieldValuesSeparatedByNewLine() throws NoSuchFieldException {
        String id_field_information = "id field information";
        when(fieldValueRenderer.render(simpleMockDto, getMockFieldByName("id"))).thenReturn(id_field_information);
        String name_field_information = "name field information";
        when(fieldValueRenderer.render(simpleMockDto, getMockFieldByName("name"))).thenReturn(name_field_information);

        String expected = "SimpleMockDto" +
                System.lineSeparator() +
                "--------------------------------------------" +
                System.lineSeparator() +
                id_field_information +
                System.lineSeparator() +
                name_field_information;

        assertThat(renderer.render(simpleMockDto), is(expected));
    }

    private Field getMockFieldByName(String id) throws NoSuchFieldException {
        return SimpleMockDto.class.getDeclaredField(id);
    }

}