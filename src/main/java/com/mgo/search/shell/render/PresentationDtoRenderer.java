package com.mgo.search.shell.render;

import com.mgo.search.model.PresentationDto;

public interface PresentationDtoRenderer<D extends PresentationDto> extends Renderer {

    String render(D dto);

}
