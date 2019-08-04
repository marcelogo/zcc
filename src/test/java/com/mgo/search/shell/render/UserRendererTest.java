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

class UserRendererTest {

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String RENDERED_ORGANIZATION = "rendered organization";
    private static final String RENDERED_USER = "rendered user";

    @Mock
    private PresentationDtoSummaryRenderer dtoSummaryRenderer;
    @Mock
    private SearchService<Ticket> ticketSearchService;
    @Mock
    private Organization organization;
    @Mock
    private User user;


    private UserRenderer userRenderer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        when(user.getOrganization()).thenReturn(organization);
        when(user.getId()).thenReturn(USER_ID);

        userRenderer = new UserRenderer(dtoSummaryRenderer, ticketSearchService);
    }

    @Test
    void renderedResultShouldContainUserSummary(){
        when(dtoSummaryRenderer.render(user)).thenReturn(RENDERED_USER);

        assertThat(userRenderer.render(user), containsString(RENDERED_USER));
    }

    @Test
    void renderedResultShouldContainUsersOrganizationSummary(){
        when(dtoSummaryRenderer.render(organization)).thenReturn(RENDERED_ORGANIZATION);

        assertThat(userRenderer.render(user), containsString(RENDERED_ORGANIZATION));
    }

    @Test
    void renderedResultShouldContainTicketSummaryWhereUserIsSubmitter(){
        Ticket submittedTicket1 = mock(Ticket.class);
        Ticket submittedTicket2 = mock(Ticket.class);

        String renderedSubmittedTicker1 = "rendered submitted ticket1";
        String renderedSubmittedTicker2 = "rendered submitted ticket2";

        when(dtoSummaryRenderer.render(submittedTicket1)).thenReturn(renderedSubmittedTicker1);
        when(dtoSummaryRenderer.render(submittedTicket2)).thenReturn(renderedSubmittedTicker2);

        Collection<Ticket> submittedTickets = Arrays.asList(submittedTicket1, submittedTicket2);
        when(ticketSearchService.search("submitter_id", USER_ID)).thenReturn(submittedTickets);

        String expected = renderedSubmittedTicker1 + TestConstants.EMPTY_LINE + renderedSubmittedTicker2;
        assertThat(userRenderer.render(user), containsString(expected));
    }

    @Test
    void renderedResultShouldContainTicketSummaryWhereUserIsAssignee(){
        Ticket assignedTicket1 = mock(Ticket.class);
        Ticket assignedTicket2 = mock(Ticket.class);

        String renderedAssignedTicker1 = "rendered assigned ticket1";
        String renderedAssignedTicker2 = "rendered assigned ticket2";

        when(dtoSummaryRenderer.render(assignedTicket1)).thenReturn(renderedAssignedTicker1);
        when(dtoSummaryRenderer.render(assignedTicket2)).thenReturn(renderedAssignedTicker2);

        Collection<Ticket> assignedTickets = Arrays.asList(assignedTicket1, assignedTicket2);
        when(ticketSearchService.search("assignee_id", USER_ID)).thenReturn(assignedTickets);

        String expected = renderedAssignedTicker1 + TestConstants.EMPTY_LINE + renderedAssignedTicker2;
        assertThat(userRenderer.render(user), containsString(expected));
    }

}