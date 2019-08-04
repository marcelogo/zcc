package com.mgo.search.converter;

import com.mgo.search.model.Organization;
import com.mgo.search.reposiory.Repository;
import com.mgo.search.reposiory.entity.OrganizationEntity;
import com.mgo.search.reposiory.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserMarshallerTest {

    @Mock
    private Repository<OrganizationEntity> orgRepository;
    @Mock
    private PresentationMarshaller<OrganizationEntity, Organization> orgMarshaller;
    @Mock
    private UserEntity entity;

    private UserMarshaller marshaller;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        marshaller = new UserMarshaller(orgRepository, orgMarshaller);
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
    void shouldMarshallName() {
        String name = "John Connor";
        when(entity.getName()).thenReturn(name);

        assertThat(marshaller.marshall(entity).getName(), is(name));
    }

    @Test
    void shouldMarshallAlias() {
        String alias = "John Baum";
        when(entity.getAlias()).thenReturn(alias);

        assertThat(marshaller.marshall(entity).getAlias(), is(alias));
    }

    @Test
    void shouldMarshallCreatedAt() {
        String createdAt = "2019-05-18T18:15:22 -10:00";
        when(entity.getCreatedAt()).thenReturn(createdAt);

        assertThat(marshaller.marshall(entity).getCreatedAt(), is(createdAt));
    }

    @Test
    void shouldMarshallActive() {
        when(entity.isActive()).thenReturn(Boolean.TRUE);

        assertThat(marshaller.marshall(entity).isActive(), is(Boolean.TRUE));
    }

    @Test
    void shouldMarshallVerified() {
        when(entity.isVerified()).thenReturn(Boolean.TRUE);

        assertThat(marshaller.marshall(entity).isVerified(), is(Boolean.TRUE));
    }

    @Test
    void shouldMarshalShared() {
        when(entity.isShared()).thenReturn(Boolean.TRUE);

        assertThat(marshaller.marshall(entity).isShared(), is(Boolean.TRUE));
    }

    @Test
    void shouldMarshallLocale() {
        String locale = Locale.ENGLISH.toString();
        when(entity.getLocale()).thenReturn(locale);

        assertThat(marshaller.marshall(entity).getLocale(), is(locale));
    }

    @Test
    void shouldMarshallTimezone() {
        String timezone =  "Moldova";
        when(entity.getTimezone()).thenReturn(timezone);

        assertThat(marshaller.marshall(entity).getTimezone(), is(timezone));
    }

    @Test
    void shouldMarshallLastLoginAt() {
        String lastLoginAt = "2019-05-18T18:15:25 -10:00";
        when(entity.getLastLoginAt()).thenReturn(lastLoginAt);

        assertThat(marshaller.marshall(entity).getLastLoginAt(), is(lastLoginAt));
    }

    @Test
    void shouldMarshallEmail() {
        String email = "marcelo@zendesk.com";
        when(entity.getEmail()).thenReturn(email);

        assertThat(marshaller.marshall(entity).getEmail(), is(email));
    }

    @Test
    void shouldMarshallPhone() {
        String phone = "8912-123-123";
        when(entity.getPhone()).thenReturn(phone);

        assertThat(marshaller.marshall(entity).getPhone(), is(phone));
    }

    @Test
    void shouldMarshallSignature() {
        String signature = "asdc-32424-vsdffbd5";
        when(entity.getSignature()).thenReturn(signature);

        assertThat(marshaller.marshall(entity).getSignature(), is(signature));
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
    void shouldMarshalSuspended() {
        when(entity.isSuspended()).thenReturn(Boolean.TRUE);

        assertThat(marshaller.marshall(entity).isSuspended(), is(Boolean.TRUE));
    }

    @Test
    void shouldMarshallRole() {
        String role = "admin";
        when(entity.getRole()).thenReturn(role);

        assertThat(marshaller.marshall(entity).getRole(), is(role));
    }

    @Test
    void shouldReturnNullWhenEntityIsNull(){
        assertThat(marshaller.marshall(null), is(nullValue()));
    }

}