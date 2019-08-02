package com.mgo.search.reposiory;

import com.mgo.search.reposiory.reader.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationJsonRepositoryTest {

    private OrganizationJsonRepository orgRepo;

    private static String ENTITY_ID = "120";

    @BeforeEach
    void setUp() throws IOException {
        orgRepo = new OrganizationJsonRepository(new ClassPathResource("orgMockData.json").getFile(), new JsonReader());
    }

    @Test
    void shouldFindAll() {
        assertThat(orgRepo.findAll(), hasSize(4));
    }

    @Test
    void shouldReadOrgId(){
        assertThat(orgRepo.byId(ENTITY_ID).getId(), is(ENTITY_ID));
    }

    @Test
    void shouldReadOrgURL(){
        assertThat(orgRepo.byId(ENTITY_ID).getUrl(), is("http://initech.zendesk.com/api/v2/organizations/120.json"));
    }

    @Test
    void shouldReadOrgExternalId(){
        assertThat(orgRepo.byId(ENTITY_ID).getExternalId(), is("82da5daf-d6ad-484d-a831-05cd3e2baea5"));
    }

    @Test
    void shouldReadOrgName(){
        assertThat(orgRepo.byId(ENTITY_ID).getName(), is("Andershun"));
    }

    @Test
    void shouldReaOrgDomainNames(){
        Set<String> tags = orgRepo.byId(ENTITY_ID).getDomainNames();

        assertThat(tags, hasSize(4));
        assertThat(tags, hasItems( "valpreal.com", "puria.com", "bostonic.com", "roughies.com"));
    }

    @Test
    void shouldReadOrgCreatedAt(){
        assertThat(orgRepo.byId(ENTITY_ID).getCreatedAt(), is("2016-01-15T04:11:08 -11:00"));
    }

    @Test
    void shouldReadOrgDetails(){
        assertThat(orgRepo.byId(ENTITY_ID).getDetails(), is("MegaCorp"));
    }

    @Test
    void shouldReadOrgSharedTickets(){
        assertTrue(orgRepo.byId(ENTITY_ID).isSharedTickets());
    }

    @Test
    void shouldReaOrgTags(){
        Set<String> tags = orgRepo.byId(ENTITY_ID).getTags();

        assertThat(tags, hasSize(4));
        assertThat(tags, hasItems(  "Robinson", "Santana", "Whitehead", "England"));
    }

}