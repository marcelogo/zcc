package com.mgo.search.index;

import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.Entity;

import java.lang.reflect.Field;
import java.util.*;
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
        entityRepository.findAll().forEach(this::indexFields);
        indexed = true;
    }

    @Override
    public List<String> indexableFieldsFor(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(f -> fieldNameResolver.fieldNameFor(f))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> search(String field, String word) {
        indexIfNeeded();

        String[] wordFragments = wordOrEmpty(word).split(STRING_SEPARATOR);

        return Arrays.stream(wordFragments)
                .map(wordFragment -> searchSingleWord(field, wordFragment))
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == wordFragments.length)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
    }

    private List<String> searchSingleWord(String field, String word) {
        Map<String, Set<String>> valueSetMap = index.getOrDefault(field, Collections.emptyMap());
        return new ArrayList<>(valueSetMap.getOrDefault(toIndexCase(word), Collections.emptySet()));
    }

    private String wordOrEmpty(String word) {
        return word.trim().isEmpty() ? EMPTY_VALUE_PLACEHOLDER : word;
    }

    private void indexFields(T entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            indexFieldValue(entity.getId(), field, fieldValueExtractor.valueAsString(entity, field));
        }
    }

    private void indexFieldValue(String entityId, Field field, String fieldValue) {
        if (nullOrEmpty(fieldValue)) {
            indexFieldValueByWord(entityId, field, EMPTY_VALUE_PLACEHOLDER);
        } else {
            Arrays.stream(fieldValue.split(STRING_SEPARATOR))
                    .forEach(word -> indexFieldValueByWord(entityId, field, word));
        }
    }

    private boolean nullOrEmpty(String fieldValue) {
        return fieldValue == null || fieldValue.trim().isEmpty();
    }

    private void indexFieldValueByWord(String entityId, Field field, String word) {
        String fieldName = fieldNameResolver.fieldNameFor(field);

        Map<String, Set<String>> fieldIndex = index.getOrDefault(fieldName, new HashMap<>());
        index.putIfAbsent(fieldName, fieldIndex);

        Set<String> wordIndex = fieldIndex.getOrDefault(toIndexCase(word), new HashSet<>());
        wordIndex.add(entityId);
        fieldIndex.putIfAbsent(toIndexCase(word), wordIndex);
    }

    private String toIndexCase(String word) {
        return word.toUpperCase();
    }

}
