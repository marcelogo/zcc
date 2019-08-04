package com.mgo.search.index;

import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.Entity;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InMemoryMapIndexService<T extends Entity> implements IndexService<T> {

    private static final String STRING_SEPARATOR = " ";
    private static final String EMPTY_VALUE_PLACEHOLDER = "____EMPTY__VALUE____";
    private Map<String, Map<String, Set<String>>> index;
    private Repository<T> entityRepository;
    private FieldValueExtractor fieldValueExtractor;
    private IndexFieldNameResolver fieldNameResolver;
    private boolean indexed = false;

    public InMemoryMapIndexService(Repository<T> entityRepository, FieldValueExtractor fieldValueExtractor, IndexFieldNameResolver fieldNameResolver) {
        this.entityRepository = entityRepository;
        this.fieldValueExtractor = fieldValueExtractor;
        this.fieldNameResolver = fieldNameResolver;
        this.index = new HashMap<>();
    }

    private void indexIfNeeded() {
        if (indexed) return;
        entityRepository.findAll().forEach(indexFields());
        indexed = true;
    }

    @Override
    public List<String> indexableFieldsFor(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(f -> fieldNameResolver.fieldNameFor(f))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> search(String field, String pattern) {
        indexIfNeeded();

        Map<String, Set<String>> valueSetMap = index.getOrDefault(field, Collections.emptyMap());
        return new ArrayList<>(valueSetMap.getOrDefault(patternOrEmpty(pattern), Collections.emptySet()));
    }

    private String patternOrEmpty(String pattern) {
        return pattern.trim().isEmpty() ? EMPTY_VALUE_PLACEHOLDER : pattern;
    }

    private Consumer<T> indexFields() {
        return entity -> {
            for (Field field : entity.getClass().getDeclaredFields()) {
                tokenizeAndIndexFieldValues(entity.getId(), field, fieldValueExtractor.valueAsString(entity, field));
            }
        };
    }

    private void tokenizeAndIndexFieldValues(String entityId, Field field, Object fieldValue) {
        if (nullOrEmpty(fieldValue)) {
            indexFieldValueByToken(entityId, field, EMPTY_VALUE_PLACEHOLDER);
        } else {
            Arrays.stream(fieldValue.toString().split(STRING_SEPARATOR))
                    .forEach(t -> indexFieldValueByToken(entityId, field, t));
        }
    }

    private boolean nullOrEmpty(Object fieldValue) {
        return fieldValue == null || fieldValue.toString().trim().isEmpty();
    }

    private void indexFieldValueByToken(String entityId, Field field, String token) {
        String fieldName = fieldNameResolver.fieldNameFor(field);

        Map<String, Set<String>> fieldIndex = index.getOrDefault(fieldName, new HashMap<>());
        index.putIfAbsent(fieldName, fieldIndex);

        Set<String> fieldTokenIndex = fieldIndex.getOrDefault(token, new HashSet<>());
        fieldTokenIndex.add(entityId);
        fieldIndex.putIfAbsent(token, fieldTokenIndex);
    }

}
