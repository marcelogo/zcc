package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

class TicketRendererTest {

    private static final String RENDERED_TICKET = "rendered ticket";
    private static final String RENDERED_ORGANIZATION = "rendered organization";
    private static final String RENDERED_SUBMITTER = "rendered submitter";
    private static final String RENDERED_ASSIGNEE = "rendered assignee";

    @Mock
    private PresentationDtoSummaryRenderer dtoSummaryRenderer;
    @Mock
    private Ticket ticket;
    @Mock
    private Organization organization;
    @Mock
    private User assignee;
    @Mock
    private User submitter;

    private TicketRenderer ticketRenderer ;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        when(ticket.getOrganization()).thenReturn(organization);
        when(ticket.getSubmitter()).thenReturn(submitter);
        when(ticket.getAssignee()).thenReturn(assignee);

        ticketRenderer = new TicketRenderer(dtoSummaryRenderer);
    }

    @Test
    void renderedResultShouldContainTicketSummary(){
        when(dtoSummaryRenderer.render(ticket)).thenReturn(RENDERED_TICKET);

        assertThat(ticketRenderer.render(ticket), containsString(RENDERED_TICKET));
    }

    @Test
    void renderedResultShouldContainOrganizationSummary(){
        when(dtoSummaryRenderer.render(organization)).thenReturn(RENDERED_ORGANIZATION);

        assertThat(ticketRenderer.render(ticket), containsString(RENDERED_ORGANIZATION));
    }

    @Test
    void renderedResultShouldContainSubmitterSummary(){
        when(dtoSummaryRenderer.render(submitter)).thenReturn(RENDERED_SUBMITTER);

        assertThat(ticketRenderer.render(ticket), containsString(RENDERED_SUBMITTER));
    }

    @Test
    void renderedResultShouldContainAssigneeSummary(){
        when(dtoSummaryRenderer.render(assignee)).thenReturn(RENDERED_ASSIGNEE);

        assertThat(ticketRenderer.render(ticket), containsString(RENDERED_ASSIGNEE));
    }

}