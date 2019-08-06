package com.mgo.search.shell.render;

import com.mgo.search.model.PresentationDto;

public class DtoNotSupported extends RuntimeException {
    private PresentationDto dto;

    DtoNotSupported(PresentationDto dto) {
        this.dto = dto;
    }

    @Override
    public String getMessage() {
        return String.format("No render support for %s", dto.getClass().getName());
    }
}
