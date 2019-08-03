package com.mgo.search.service;

import com.mgo.search.index.IndexFieldNameResolver;
import com.mgo.search.reposiory.entity.Entity;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.entity.UserEntity;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchableFieldService {

    private IndexFieldNameResolver fieldNameResolver;

    public SearchableFieldService(IndexFieldNameResolver fieldNameResolver) {
        this.fieldNameResolver = fieldNameResolver;
    }

    public List<String> searchableFieldsFor(EntityType entityType) {
        return Arrays.stream(getEntityClassFor(entityType).getDeclaredFields())
                .map(f -> fieldNameResolver.fieldNameFor(f))
                .collect(Collectors.toList());
    }

    private Class<? extends Entity> getEntityClassFor(EntityType entityType) {
        if (EntityType.ORGANIZATION.equals(entityType)) {
            return OrganizationEntity.class;
        } else if (EntityType.USER.equals(entityType)) {
            return UserEntity.class;
        } else if (EntityType.TICKET.equals(entityType)) {
            return TicketEntity.class;
        }

        throw new InvalidParameterException("Entity type hasn't been implemented yet.");
    }
}
