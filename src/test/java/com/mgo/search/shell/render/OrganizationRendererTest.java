package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrganizationRendererTest {
    private static final String RENDERED_ORGANIZATION = "rendered organization";
    private static final String ORG_ID = UUID.randomUUID().toString();

    @Mock
    private PresentationDtoSummaryRenderer dtoSummaryRenderer;
    @Mock
    private SearchService<User> userSearchService;
    @Mock
    private SearchService<Ticket> ticketSearchService;
    @Mock
    private Organization organization;

    private OrganizationRenderer organizationRenderer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        when(organization.getId()).thenReturn(ORG_ID);

        organizationRenderer = new OrganizationRenderer(dtoSummaryRenderer, userSearchService, ticketSearchService);
    }

    @Test
    void renderedResultShouldContainOrganizationSummary(){
        when(dtoSummaryRenderer.render(organization)).thenReturn(RENDERED_ORGANIZATION);

        assertThat(organizationRenderer.render(organization), containsString(RENDERED_ORGANIZATION));
    }

    @Test
    void renderedResultShouldContainSummaryOfUsersFromOrganization(){
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        String renderedUser1 = "rendered user1";
        String renderedUser2 = "rendered user2";

        when(dtoSummaryRenderer.render(user1)).thenReturn(renderedUser1);
        when(dtoSummaryRenderer.render(user2)).thenReturn(renderedUser2);

        Collection<User> organizationUsers = Arrays.asList(user1, user2);
        when(userSearchService.search("organization_id", ORG_ID)).thenReturn(organizationUsers);

        String expected = renderedUser1 + TestConstants.LINE_SEPARATOR + renderedUser2;
        assertThat(organizationRenderer.render(organization), containsString(expected));
    }

    @Test
    void renderedResultShouldContainSummaryOfTicketsFromOrganization(){
        Ticket ticket1 = mock(Ticket.class);
        Ticket ticket2 = mock(Ticket.class);

        String renderedTicker1 = "rendered ticket1";
        String renderedTicker2 = "rendered ticket2";

        when(dtoSummaryRenderer.render(ticket1)).thenReturn(renderedTicker1);
        when(dtoSummaryRenderer.render(ticket2)).thenReturn(renderedTicker2);

        Collection<Ticket> organizationTickets = Arrays.asList(ticket1, ticket2);
        when(ticketSearchService.search("organization_id", ORG_ID)).thenReturn(organizationTickets);

        String expected = renderedTicker1 + TestConstants.LINE_SEPARATOR + renderedTicker2;
        assertThat(organizationRenderer.render(organization), containsString(expected));
    }
}