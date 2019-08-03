package com.mgo.search.shell.render;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SearchableFieldRendererTest {

    private SearchableFieldRenderer renderer = new SearchableFieldRenderer();

    @Test
    void shouldCreateStringWithEntityNameFollowedByFieldNamesSeparatedByNewLine(){
        String actual = renderer.renderEntityAndFields("Entity", Arrays.asList("field1", "field2"));

        String expected = new StringBuilder("Entity")
                .append(System.lineSeparator())
                .append("------------------------------------------------")
                .append(System.lineSeparator())
                .append("    field1")
                .append(System.lineSeparator())
                .append("    field2")
                .toString();

        assertThat(actual, is(expected));

    }

}