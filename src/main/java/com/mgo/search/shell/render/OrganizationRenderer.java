package com.mgo.search.shell.render;

import com.mgo.search.model.Organization;
import com.mgo.search.model.Ticket;
import com.mgo.search.model.User;
import com.mgo.search.service.SearchService;

import java.util.stream.Collectors;

public class OrganizationRenderer implements PresentationDtoRenderer<Organization> {

    private static final String ORGANIZATION_ID_FIELD = "organization_id";
    private PresentationDtoSummaryRenderer presentationDtoSummaryRenderer;
    private SearchService<User> userSearchService;
    private SearchService<Ticket> ticketSearchService;

    public OrganizationRenderer(PresentationDtoSummaryRenderer presentationDtoSummaryRenderer,
                                SearchService<User> userSearchService,
                                SearchService<Ticket> ticketSearchService) {
        this.presentationDtoSummaryRenderer = presentationDtoSummaryRenderer;
        this.userSearchService = userSearchService;
        this.ticketSearchService = ticketSearchService;
    }

    public String render(Organization organization) {
        return "Organization Details" +
                HORIZONTAL_BAR +
                renderOrganization(organization) +
                LINE_SEPARATOR +
                String.format("User from %s organization", organization.getName()) +
                HORIZONTAL_BAR +
                renderUsersFromOrganization(organization.getId()) +
                LINE_SEPARATOR +
                String.format("Tickets from %s organization", organization.getName()) +
                HORIZONTAL_BAR +
                renderTicketsFromOrganization(organization.getId());
    }

    private String renderTicketsFromOrganization(String orgId) {
        return ticketSearchService.search(ORGANIZATION_ID_FIELD, orgId).stream()
                .map(user -> presentationDtoSummaryRenderer.render(user))
                .collect(Collectors.joining(System.lineSeparator()));

    }

    private String renderUsersFromOrganization(String orgId) {
        return userSearchService.search(ORGANIZATION_ID_FIELD, orgId).stream()
                .map(user -> presentationDtoSummaryRenderer.render(user))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String renderOrganization(Organization organization) {
        return presentationDtoSummaryRenderer.render(organization);
    }
}
