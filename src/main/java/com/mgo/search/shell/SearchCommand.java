package com.mgo.search.shell;

import com.mgo.search.model.PresentationDto;
import com.mgo.search.service.EntityType;
import com.mgo.search.service.SearchService;
import com.mgo.search.shell.render.PresentationDtoRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@ShellComponent
public class SearchCommand {

    private static final CharSequence DOUBLE_NEW_LINE = System.lineSeparator() + System.lineSeparator();

    private Map<EntityType, SearchService> entitySearchMap;
    private PresentationDtoRenderer renderer;

    @Autowired
    public SearchCommand(Map<EntityType, SearchService> searchServiceMap, PresentationDtoRenderer renderer) {
        super();

        this.entitySearchMap = searchServiceMap;
        this.renderer = renderer;
    }

    @SuppressWarnings("unchecked")
    @ShellMethod("Ful word search on fields of Organizations, Users or Tickets")
    public String search(@ShellOption(help = "Entity name. It can be [Organization | User | Ticket]") String entity,
                         @ShellOption(help = "Field to search. For field info see show-fields command.") String field,
                         @ShellOption(help = "Value to search (full work match only)") String word) {

        try {
            Collection<? extends PresentationDto> results = getServiceForEntity(entity).search(field, word);

            if (results.isEmpty()) {
                return "No results found for criteria.";
            }

            return results.stream().map(renderer::render).collect(Collectors.joining(DOUBLE_NEW_LINE));
        } catch (IllegalAccessException e) {
            return e.getMessage();
        }
    }

    private SearchService getServiceForEntity(String entity) throws IllegalAccessException {
        try {
            return entitySearchMap.get(EntityType.valueOf(entity.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalAccessException(String.format("Invalid entity [%s]. Possible values are [Organization | User | Ticket]", entity));
        }
    }
}
