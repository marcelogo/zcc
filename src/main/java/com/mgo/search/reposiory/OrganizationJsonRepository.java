package com.mgo.search.reposiory;

import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.reader.JsonReader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class OrganizationJsonRepository implements Repository<OrganizationEntity> {
    private Map<String, OrganizationEntity> organizationEntityMap;

    public OrganizationJsonRepository(File dataFile, JsonReader jsonReader) throws IOException {
        organizationEntityMap = jsonReader.read(dataFile, OrganizationEntity.class);
    }

    @Override
    public OrganizationEntity byId(String id) {
        return organizationEntityMap.get(id);
    }

    @Override
    public Collection<OrganizationEntity> findAll() {
        return organizationEntityMap.values();
    }
}
