package com.mgo.search.index;

import com.mgo.search.reposiory.entity.Entity;

import java.util.Collection;

class IndexableMockEntity implements Entity {

    private String id;
    private String stringField;
    private long longField;
    private int intField;
    private boolean booleanField;
    private Collection<Object> collectionField;
    private Object nullableField;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public long getLongField() {
        return longField;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public Collection<Object> getCollectionField() {
        return collectionField;
    }

    public void setCollectionField(Collection<Object> collectionField) {
        this.collectionField = collectionField;
    }

    public Object getNullableField() {
        return nullableField;
    }

    public void setNullableField(Object nullableField) {
        this.nullableField = nullableField;
    }
}
