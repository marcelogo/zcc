package com.mgo.search.reposiory;

import com.mgo.search.reposiory.entity.UserEntity;
import com.mgo.search.reposiory.reader.JsonReader;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UserJsonRepository implements Repository<UserEntity> {
    private Map<String, UserEntity> userMap;

    public UserJsonRepository(File dataFile, JsonReader jsonReader) throws IOException {
        userMap = jsonReader.read(dataFile, UserEntity.class);
    }

    @Override
    public UserEntity byId(String id) {
        return userMap.get(id);
    }

    @Override
    public Collection<UserEntity> findAll() {
        return userMap.values();
    }
}
