package com.mgo.search.index;

import com.google.gson.annotations.SerializedName;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.Entity;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public class InMemoryMapIndexService<T extends Entity> implements IndexService {

    private static final String STRING_SEPARATOR = " ";
    private Map<String, Map<String, Set<String>>> index;
    private Repository<T> entityRepository;
    private FieldValueExtractor fieldValueExtractor;
    private boolean indexed = false;

    public InMemoryMapIndexService(Repository<T> entityRepository, FieldValueExtractor fieldValueExtractor) {
        this.entityRepository = entityRepository;
        this.fieldValueExtractor = fieldValueExtractor;
        this.index = new HashMap<>();
    }

    private void indexIfNeeded() {
        if (indexed) return;
        entityRepository.findAll().forEach(indexFields());
        indexed = true;
    }

    @Override
    public Collection<String> search(String field, String pattern) {
        indexIfNeeded();
        Map<String, Set<String>> valueSetMap = index.getOrDefault(field, Collections.emptyMap());
        return new ArrayList<>(valueSetMap.getOrDefault(pattern, Collections.emptySet()));
    }

    private Consumer<T> indexFields() {
        return entity -> {
            for (Field field : entity.getClass().getDeclaredFields()) {
                tokenizeAndIndexFieldValues(entity.getId(), field, fieldValueExtractor.valueAsString(entity, field));
            }
        };
    }

    private void tokenizeAndIndexFieldValues(String entityId, Field field, Object fieldValue) {
        Arrays.stream(fieldValue.toString().split(STRING_SEPARATOR))
                .forEach(t -> indexFieldValueByToken(entityId, field, t));
    }

    private void indexFieldValueByToken(String entityId, Field field, String token) {
        String fieldName = jsonFieldNameFor(field);

        Map<String, Set<String>> fieldIndex = index.getOrDefault(fieldName, new HashMap<>());
        index.putIfAbsent(fieldName, fieldIndex);

        Set<String> fieldTokenIndex = fieldIndex.getOrDefault(token, new HashSet<>());
        fieldTokenIndex.add(entityId);
        fieldIndex.putIfAbsent(token, fieldTokenIndex);
    }

    private String jsonFieldNameFor(Field field) {
        SerializedName serializedName = field.getAnnotation(SerializedName.class);
        return serializedName != null ? serializedName.value() : field.getName();
    }

}
