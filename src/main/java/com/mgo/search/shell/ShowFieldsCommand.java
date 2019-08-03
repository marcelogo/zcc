package com.mgo.search.shell;

import com.mgo.search.service.EntityType;
import com.mgo.search.shell.render.SearchableFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Arrays;
import java.util.stream.Collectors;

@ShellComponent
public class ShowFieldsCommand {

    private SearchableFieldRenderer searchableFieldRenderer;

    @Autowired
    public ShowFieldsCommand(SearchableFieldRenderer searchableFieldRenderer) {
        super();
        this.searchableFieldRenderer = searchableFieldRenderer;
    }

    @ShellMethod("Display searchable fields for searchable entities")
    public String showFields() {
        return Arrays.stream(EntityType.values())
                .map(entityType -> searchableFieldRenderer.renderForType(entityType))
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
