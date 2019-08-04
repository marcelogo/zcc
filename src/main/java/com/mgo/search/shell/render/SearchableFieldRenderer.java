package com.mgo.search.shell.render;

import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchableFieldService;

import java.util.Collection;
import java.util.stream.Collectors;

public class SearchableFieldRenderer implements Renderer {

    private SearchableFieldService searchableFieldService;

    public SearchableFieldRenderer(SearchableFieldService searchableFieldService) {
        this.searchableFieldService = searchableFieldService;
    }

    public String renderForType(EntityType entityType) {
        return renderEntityAndFields(entityType.name(), searchableFieldService.searchableFieldsFor(entityType));
    }

    private String renderEntityAndFields(String entityName, Collection<String> fields) {
        return entityName +
                HORIZONTAL_BAR +
                fields.stream().map(f -> "    " + f).collect(Collectors.joining(System.lineSeparator()));
    }
}
