package com.mgo.search.shell.render;

import com.mgo.search.model.Ticket;

public class TicketRenderer implements PresentationDtoRenderer<Ticket> {

    private PresentationDtoSummaryRenderer presentationDtoSummaryRenderer;

    public TicketRenderer(PresentationDtoSummaryRenderer presentationDtoSummaryRenderer) {
        this.presentationDtoSummaryRenderer = presentationDtoSummaryRenderer;
    }

    public String render(Ticket ticket) {
        return "Ticket Details" +
                HORIZONTAL_BAR +
                presentationDtoSummaryRenderer.render(ticket) +
                LINE_SEPARATOR +
                "Ticket's organization details" +
                HORIZONTAL_BAR +
                presentationDtoSummaryRenderer.render(ticket.getOrganization()) +
                LINE_SEPARATOR +
                "Submitter" +
                HORIZONTAL_BAR +
                presentationDtoSummaryRenderer.render(ticket.getSubmitter()) +
                LINE_SEPARATOR +
                "Assignee" +
                HORIZONTAL_BAR +
                presentationDtoSummaryRenderer.render(ticket.getAssignee());
    }

}
