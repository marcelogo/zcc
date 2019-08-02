package com.mgo.search.reposiory;

import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.reader.JsonReader;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class TicketJsonRepository implements Repository<TicketEntity> {
    private Map<String, TicketEntity> ticketMap;

    public TicketJsonRepository(File dataFile, JsonReader jsonReader) throws IOException {
        ticketMap = jsonReader.read(dataFile, TicketEntity.class);
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
