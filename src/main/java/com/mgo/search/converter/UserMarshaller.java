package com.mgo.search.converter;

import com.mgo.search.model.Organization;
import com.mgo.search.model.User;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.UserEntity;

import java.util.*;

public class UserMarshaller implements PresentationMarshaller<UserEntity, User> {

    private Repository<OrganizationEntity> orgRepository;
    private PresentationMarshaller<OrganizationEntity, Organization> orgMarshaller;

    public UserMarshaller (Repository<OrganizationEntity> orgRepository,
                           PresentationMarshaller<OrganizationEntity, Organization> orgMarshaller){
        this.orgRepository = orgRepository;
        this.orgMarshaller = orgMarshaller;
    }

    @Override
    public User marshall(UserEntity entity) {

        if (entity == null) return null;

        User user = new User();
        user.setId(entity.getId());
        user.setUrl(entity.getUrl());
        user.setExternalId(entity.getExternalId());
        user.setName(entity.getName());
        user.setAlias(entity.getAlias());
        user.setCreatedAt(entity.getCreatedAt());
        user.setActive(entity.isActive());
        user.setVerified(entity.isVerified());
        user.setShared(entity.isShared());
        user.setLocale(entity.getLocale());
        user.setTimezone(entity.getTimezone());
        user.setLastLoginAt(entity.getLastLoginAt());
        user.setEmail(entity.getEmail());
        user.setPhone(entity.getPhone());
        user.setSignature(entity.getSignature());
        user.setTags(new HashSet<>(entity.getTags()));
        user.setSuspended(entity.isSuspended());
        user.setRole(entity.getRole());
        user.setOrganization(getOrgById(entity.getOrganizationId()));
        return user;
    }

    private Organization getOrgById(String organizationId) {
        return orgMarshaller.marshall(orgRepository.byId(organizationId));
    }

}
