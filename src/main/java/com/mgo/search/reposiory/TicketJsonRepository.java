package com.mgo.search.reposiory;

import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.reader.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public class TicketJsonRepository implements Repository<TicketEntity> {
    private Map<String, TicketEntity> ticketMap;

    public TicketJsonRepository(InputStream data, JsonReader jsonReader) throws IOException {
        ticketMap = jsonReader.read(data, TicketEntity.class);
    }

    @Override
    public TicketEntity byId(String id) {
        return ticketMap.get(id);
    }

    @Override
    public Collection<TicketEntity> findAll() {
        return ticketMap.values();
    }
}
