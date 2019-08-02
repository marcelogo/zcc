package com.mgo.search.converter;

import com.mgo.search.reposiory.entity.OrganizationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

class OrganizationMarshallerTest {

    @Mock
    private OrganizationEntity entity;

    private OrganizationMarshaller marshaller = new OrganizationMarshaller();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
        String name = "Zendesk";
        when(entity.getName()).thenReturn(name);

        assertThat(marshaller.marshall(entity).getName(), is(name));
    }

    @Test
    void shouldMarshallDomainNames() {
        String domain1 = "www.a.com";
        String domain2 = "www.b.org";
        Set<String> domainNames = Sets.newSet(domain1, domain2);
        when(entity.getDomainNames()).thenReturn(domainNames);

        Set<String> marshalled = marshaller.marshall(entity).getDomainNames();
        assertThat(marshalled, hasSize(2));
        assertThat(marshalled, hasItems(domain1, domain2));
    }

    @Test
    void shouldMarshallCreatedAt() {
        String createdAt = "2016-04-10T11:12:35 -10:00";
        when(entity.getCreatedAt()).thenReturn(createdAt);

        assertThat(marshaller.marshall(entity).getCreatedAt(), is(createdAt));
    }

    @Test
    void shouldMarshallDetails() {
        String details = "This is just a minor detail";
        when(entity.getDetails()).thenReturn(details);

        assertThat(marshaller.marshall(entity).getDetails(), is(details));
    }

    @Test
    void shouldMarshallSharedTickets() {
        when(entity.isSharedTickets()).thenReturn(Boolean.TRUE);

        assertThat(marshaller.marshall(entity).isSharedTickets(), is(Boolean.TRUE));
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
}