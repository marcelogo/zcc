package com.mgo.search.converter;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.entity.UserEntity;

import java.util.HashSet;

public class TicketMarshaller implements PresentationMarshaller<TicketEntity, Ticket> {

    private Repository<OrganizationEntity> orgRepository;
    private PresentationMarshaller<OrganizationEntity, Organization> orgMarshaller;
    private Repository<UserEntity> userEntityRepository;
    private PresentationMarshaller<UserEntity, User> userMarshaller;

    public TicketMarshaller(Repository<OrganizationEntity> orgRepository,
                            PresentationMarshaller<OrganizationEntity, Organization> orgMarshaller,
                            Repository<UserEntity> userRepository,
                            PresentationMarshaller<UserEntity, User> userMarshaller){
        this.orgRepository = orgRepository;
        this.orgMarshaller = orgMarshaller;
        this.userEntityRepository = userRepository;
        this.userMarshaller = userMarshaller;
    }

    @Override
    public Ticket marshall(TicketEntity entity) {

        if (entity == null) return null;

        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setUrl(entity.getUrl());
        ticket.setExternalId(entity.getExternalId());
        ticket.setCreatedAt(entity.getCreatedAt());
        ticket.setType(entity.getType());
        ticket.setSubject(entity.getSubject());
        ticket.setDescription(entity.getDescription());
        ticket.setPriority(entity.getPriority());
        ticket.setStatus(entity.getStatus());
        ticket.setChannel(entity.getVia());
        ticket.setTags(new HashSet<>(entity.getTags()));
        ticket.setOrganization(getOrgById(entity.getOrganizationId()));
        ticket.setAssignee(getUserById(entity.getAssigneeId()));
        ticket.setSubmitter(getUserById(entity.getSubmitterId()));
        ticket.setHasIncidents(entity.hasIncidents());
        return ticket;
    }

    private Organization getOrgById(String organizationId) {
        return orgMarshaller.marshall(orgRepository.byId(organizationId));
    }

    private User getUserById(String userId) {
        return userMarshaller.marshall(userEntityRepository.byId(userId));
    }
}
