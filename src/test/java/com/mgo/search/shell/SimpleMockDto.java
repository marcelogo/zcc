package com.mgo.search.shell;

import com.mgo.search.model.PresentationDto;

public class SimpleMockDto implements PresentationDto {
    private int id;
    private String name;

    public SimpleMockDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
