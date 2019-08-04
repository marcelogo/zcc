package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.service.SearchService;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserRenderer implements PresentationDtoRenderer<User> {

    private PresentationDtoSummaryRenderer presentationDtoSummaryRenderer;
    private SearchService<Ticket> ticketSearchService;

    public UserRenderer(PresentationDtoSummaryRenderer presentationDtoSummaryRenderer,
                        SearchService<Ticket> ticketSearchService) {
        this.presentationDtoSummaryRenderer = presentationDtoSummaryRenderer;
        this.ticketSearchService = ticketSearchService;
    }

    public String render(User user) {
        return "User Details" +
                HORIZONTAL_BAR +
                renderUser(user) +
                LINE_SEPARATOR +
                "User's organization details" +
                HORIZONTAL_BAR +
                renderOrganizationFromUser(user.getOrganization()) +
                LINE_SEPARATOR +
                "Tickets where user is submitter" +
                HORIZONTAL_BAR +
                renderTicketsFromSubmitter(user.getId()) +
                LINE_SEPARATOR +
                "Tickets where user is assignee" +
                HORIZONTAL_BAR +
                renderTicketsFromAssignee(user.getId());
    }

    private String renderTicketsFromSubmitter(String userId) {
        return renderTickets(ticketSearchService.search("submitter_id", userId));
    }

    private String renderTicketsFromAssignee(String userId) {
        return renderTickets(ticketSearchService.search("assignee_id", userId));
    }

    private String renderTickets(Collection<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> presentationDtoSummaryRenderer.render(ticket))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String renderOrganizationFromUser(Organization organization) {
        return presentationDtoSummaryRenderer.render(organization);
    }

    private String renderUser(User user) {
        return presentationDtoSummaryRenderer.render(user);
    }
}
