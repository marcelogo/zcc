package com.mgo.search.shell.render;

import com.mgo.search.model.PresentationDto;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PresentationDtoSummaryRenderer implements Renderer {
    private static final String EMPTY_SUMMARY = "Data not found or not assigned";
    private FieldValueRenderer fieldValueRenderer;

    public PresentationDtoSummaryRenderer(FieldValueRenderer fieldValueRenderer) {
        this.fieldValueRenderer = fieldValueRenderer;
    }

    public String render(PresentationDto dto) {
        if (dto == null) return EMPTY_SUMMARY;

        return dto.getClass().getSimpleName() +
                HORIZONTAL_BAR +
                Arrays.stream(dto.getClass().getDeclaredFields())
                        .map(field -> fieldValueRenderer.render(dto, field))
                        .collect(Collectors.joining(System.lineSeparator()));
    }

}
