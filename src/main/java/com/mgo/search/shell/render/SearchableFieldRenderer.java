package com.mgo.search.shell.render;

import java.util.Collection;
import java.util.stream.Collectors;

public class SearchableFieldRenderer {

    public String renderEntityAndFields(String entityName, Collection<String> fields){
        return System.lineSeparator() +
                entityName +
                System.lineSeparator() +
                "------------------------------------------------" +
                System.lineSeparator() +
                fields.stream().map(f -> "    " + f).collect(Collectors.joining(System.lineSeparator()));
    }
}
