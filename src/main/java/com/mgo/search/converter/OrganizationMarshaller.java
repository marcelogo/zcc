package com.mgo.search.converter;

import com.mgo.search.model.Organization;
import com.mgo.search.reposiory.entity.OrganizationEntity;

import java.util.HashSet;

public class OrganizationMarshaller implements PresentationMarshaller<OrganizationEntity, Organization> {

    @Override
    public Organization marshall(OrganizationEntity entity) {
        Organization org = new Organization();
        org.setId(entity.getId());
        org.setUrl(entity.getUrl());
        org.setExternalId(entity.getExternalId());
        org.setName(entity.getName());
        org.setDomainNames(new HashSet<>(entity.getDomainNames()));
        org.setCreatedAt(entity.getCreatedAt());
        org.setDetails(entity.getDetails());
        org.setSharedTickets(entity.isSharedTickets());
        org.setTags(new HashSet<>(entity.getTags()));
        return org;
    }

}
