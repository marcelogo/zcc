package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.PresentationDto;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;

import java.util.Collection;

class MockPresentationDto implements PresentationDto {

    private String stringField;
    private long longField;
    private int intField;
    private boolean booleanField;
    private Collection<Object> collectionField;
    private Object nullableField;
    private Organization organization;
    private User user;
    private Ticket ticket;

    public String getStringField() {
        return stringField;
    }

    void setStringField(String stringField) {
        this.stringField = stringField;
    }

    long getLongField() {
        return longField;
    }

    void setLongField(long longField) {
        this.longField = longField;
    }

    int getIntField() {
        return intField;
    }

    void setIntField(int intField) {
        this.intField = intField;
    }

    boolean isBooleanField() {
        return booleanField;
    }

    void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    Collection<Object> getCollectionField() {
        return collectionField;
    }

    void setCollectionField(Collection<Object> collectionField) {
        this.collectionField = collectionField;
    }

    Object getNullableField() {
        return nullableField;
    }

    void setNullableField(Object nullableField) {
        this.nullableField = nullableField;
    }

    Organization getOrganization() {
        return organization;
    }

    void setOrganization(Organization organization) {
        this.organization = organization;
    }

    User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    Ticket getTicket() {
        return ticket;
    }

    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
