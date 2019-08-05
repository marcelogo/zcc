package com.mgo.search.reposiory.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mgo.search.reposiory.entity.Entity;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonReader {

    public <T extends Entity> Map<String, T> read(InputStream data, Class<T> clazz) {
        InputStreamReader fr = new InputStreamReader(data);
        return toEntityIndex(new Gson().fromJson(fr, TypeToken.getParameterized(List.class, clazz).getType()));
    }

    private <T extends Entity> Map<String, T> toEntityIndex(List<T> fromJson) {
        Map<String, T> entityIdMap = new HashMap<>();

        for (T e : fromJson) {
            entityIdMap.put(e.getId(), e);
        }

        return entityIdMap;
    }

}
