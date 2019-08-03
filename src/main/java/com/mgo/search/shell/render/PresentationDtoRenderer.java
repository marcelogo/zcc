package com.mgo.search.shell.render;

import com.mgo.search.model.PresentationDto;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PresentationDtoRenderer {
    private FieldValueRenderer fieldValueRenderer;

    public PresentationDtoRenderer(FieldValueRenderer fieldValueRenderer) {
        this.fieldValueRenderer = fieldValueRenderer;
    }

    public String render(PresentationDto dto){
        return dto.getClass().getSimpleName() +
                System.lineSeparator() +
                "--------------------------------------------" +
                System.lineSeparator() +
                Arrays.stream(dto.getClass().getDeclaredFields())
                .map(field -> fieldValueRenderer.render(dto, field))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
