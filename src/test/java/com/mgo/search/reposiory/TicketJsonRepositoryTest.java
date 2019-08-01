package com.mgo.search.reposiory;

import com.mgo.search.reposiory.reader.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketJsonRepositoryTest {

    private TicketJsonRepository ticketRepo;

    private static String ENTITY_ID = "1a227508-9f39-427c-8f57-1b72f3fab87c";

    @BeforeEach
    void setUp() throws IOException {
        ticketRepo = new TicketJsonRepository("ticketMockData.json", new JsonReader());
    }

    @Test
    void shouldFindAll() {
        assertThat(ticketRepo.findAll(), hasSize(2));
    }

    @Test
    void shouldReadTicketId(){
        assertThat(ticketRepo.byId(ENTITY_ID).getId(), is(ENTITY_ID));
    }

    @Test
    void shouldReadTicketURL(){
        assertThat(ticketRepo.byId(ENTITY_ID).getUrl(), is("http://initech.zendesk.com/api/v2/tickets/1a227508-9f39-427c-8f57-1b72f3fab87c.json"));
    }

    @Test
    void shouldReadTicketExternalId(){
        assertThat(ticketRepo.byId(ENTITY_ID).getExternalId(), is("3e5ca820-cd1f-4a02-a18f-11b18e7bb49a"));
    }

    @Test
    void shouldReadTicketCreatedAt(){
        assertThat(ticketRepo.byId(ENTITY_ID).getCreatedAt(), is("2016-04-14T08:32:31 -10:00"));
    }

    @Test
    void shouldReadTicketType(){
        assertThat(ticketRepo.byId(ENTITY_ID).getType(), is("incident"));
    }

    @Test
    void shouldReadTicketSubject(){
        assertThat(ticketRepo.byId(ENTITY_ID).getSubject(), is("A Catastrophe in Micronesia"));
    }

    @Test
    void shouldReadTicketDescription(){
        assertThat(ticketRepo.byId(ENTITY_ID).getDescription(), is("Aliquip excepteur fugiat ex minim ea aute eu labore. Sunt eiusmod esse eu non commodo est veniam consequat."));
    }

    @Test
    void shouldReadTicketPriority(){
        assertThat(ticketRepo.byId(ENTITY_ID).getPriority(), is("low"));
    }

    @Test
    void shouldReadTicketStatus(){
        assertThat(ticketRepo.byId(ENTITY_ID).getStatus(), is("hold"));
    }

    @Test
    void shouldReadTicketSubmitterId(){
        assertThat(ticketRepo.byId(ENTITY_ID).getSubmitterId(), is("71"));
    }

    @Test
    void shouldReadTicketAssigneeId(){
        assertThat(ticketRepo.byId(ENTITY_ID).getAssigneeId(), is("38"));
    }

    @Test
    void shouldReadTicketOrganizationId(){
        assertThat(ticketRepo.byId(ENTITY_ID).getOrganizationId(), is("112"));
    }

    @Test
    void shouldReadTicketTags(){
        Set<String> tags = ticketRepo.byId(ENTITY_ID).getTags();

        assertThat(tags, hasSize(4));
        assertThat(tags, hasItems("Puerto Rico", "Idaho", "Oklahoma", "Louisiana"));
    }

    @Test
    void shouldReadTicketHasIncidents(){
        assertTrue(ticketRepo.byId(ENTITY_ID).hasIncidents());
    }

    @Test
    void shouldReadTicketDueAt(){
        assertThat(ticketRepo.byId(ENTITY_ID).getDueAt(), is("2016-08-15T05:37:32 -10:00"));
    }

    @Test
    void shouldReadTicketVia(){
        assertThat(ticketRepo.byId(ENTITY_ID).getVia(), is("chat"));
    }

}