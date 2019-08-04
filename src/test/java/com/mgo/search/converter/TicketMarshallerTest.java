package com.mgo.search.converter;

import com.mgo.search.model.Organization;
import com.mgo.search.model.User;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.TicketEntity;
import com.mgo.search.reposiory.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketMarshallerTest {

    @Mock
    private Repository<OrganizationEntity> orgRepository;
    @Mock
    private PresentationMarshaller<OrganizationEntity, Organization> orgMarshaller;
    @Mock
    private Repository<UserEntity> userRepository;
    @Mock
    private PresentationMarshaller<UserEntity, User> userMarshaller;
    @Mock
    private TicketEntity entity;

    private TicketMarshaller marshaller;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        marshaller = new TicketMarshaller(orgRepository, orgMarshaller, userRepository, userMarshaller);
    }

    @Test
    void shouldMarshallId() {
        String id = UUID.randomUUID().toString();
        when(entity.getId()).thenReturn(id);

        assertThat(marshaller.marshall(entity).getId(), is(id));
    }

    @Test
    void shouldMarshallUrl() {
        String url = "http://www.zendesk.com";
        when(entity.getUrl()).thenReturn(url);

        assertThat(marshaller.marshall(entity).getUrl(), is(url));
    }

    @Test
    void shouldMarshallExternalId() {
        String externalID = UUID.randomUUID().toString();
        when(entity.getExternalId()).thenReturn(externalID);

        assertThat(marshaller.marshall(entity).getExternalId(), is(externalID));
    }

    @Test
    void shouldMarshallCreatedAt() {
        String createdAt = "2019-05-18T18:15:22 -10:00";
        when(entity.getCreatedAt()).thenReturn(createdAt);

        assertThat(marshaller.marshall(entity).getCreatedAt(), is(createdAt));
    }

    @Test
    void shouldMarshallType() {
        String type = "incident";
        when(entity.getType()).thenReturn(type);

        assertThat(marshaller.marshall(entity).getType(), is(type));
    }

    @Test
    void shouldMarshallSubject() {
        String subject = "incident";
        when(entity.getSubject()).thenReturn(subject);

        assertThat(marshaller.marshall(entity).getSubject(), is(subject));
    }

    @Test
    void shouldMarshallDescription() {
        String description = "descriptive description";
        when(entity.getDescription()).thenReturn(description);

        assertThat(marshaller.marshall(entity).getDescription(), is(description));
    }

    @Test
    void shouldMarshallPriority() {
        String priority = "low";
        when(entity.getPriority()).thenReturn(priority);

        assertThat(marshaller.marshall(entity).getPriority(), is(priority));
    }

    @Test
    void shouldMarshallStatus() {
        String status = "closed";
        when(entity.getStatus()).thenReturn(status);

        assertThat(marshaller.marshall(entity).getStatus(), is(status));
    }

    @Test
    void shouldMarshalSubmitter() {
        String submitterId = UUID.randomUUID().toString();
        UserEntity userEntity = mock(UserEntity.class);
        when(userRepository.byId(submitterId)).thenReturn(userEntity);
        User submitter = mock(User.class);
        when(userMarshaller.marshall(userEntity)).thenReturn(submitter);
        when(entity.getSubmitterId()).thenReturn(submitterId);

        assertThat(marshaller.marshall(entity).getSubmitter(), is(submitter));
    }

    @Test
    void shouldMarshalAssignee() {
        String assigneeId = UUID.randomUUID().toString();
        UserEntity userEntity = mock(UserEntity.class);
        when(userRepository.byId(assigneeId)).thenReturn(userEntity);
        User assignee = mock(User.class);
        when(userMarshaller.marshall(userEntity)).thenReturn(assignee);
        when(entity.getAssigneeId()).thenReturn(assigneeId);

        assertThat(marshaller.marshall(entity).getAssignee(), is(assignee));
    }

    @Test
    void shouldMarshallOrganization() {
        String orgId = UUID.randomUUID().toString();
        OrganizationEntity orgEntity = mock(OrganizationEntity.class);
        when(orgRepository.byId(orgId)).thenReturn(orgEntity);
        Organization org = mock(Organization.class);
        when(orgMarshaller.marshall(orgEntity)).thenReturn(org);
        when(entity.getOrganizationId()).thenReturn(orgId);

        assertThat(marshaller.marshall(entity).getOrganization(), is(org));
    }

    @Test
    void shouldMarshallTags() {
        String tag1 = "tag1";
        String tag2 = "tag2";
        Set<String> tags = Sets.newSet(tag1, tag2);
        when(entity.getTags()).thenReturn(tags);

        Set<String> marshalled = marshaller.marshall(entity).getTags();
        assertThat(marshalled, hasSize(2));
        assertThat(marshalled, hasItems(tag1, tag2));
    }

    @Test
    void shouldMarshallHasIncidents() {
        when(entity.hasIncidents()).thenReturn(Boolean.TRUE);

        assertThat(marshaller.marshall(entity).hasIncidents(), is(Boolean.TRUE));
    }

    @Test
    void shouldMarshallChannel() {
        String channel = "chat";
        when(entity.getVia()).thenReturn(channel);

        assertThat(marshaller.marshall(entity).getChannel(), is(channel));
    }

    @Test
    void shouldReturnNullWhenEntityIsNull(){
        assertThat(marshaller.marshall(null), is(nullValue()));
    }

}