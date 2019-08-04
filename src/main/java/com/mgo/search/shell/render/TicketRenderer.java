package com.mgo.search.shell.render;

import com.mgo.search.model.Ticket;

public class TicketRenderer implements PresentationDtoRenderer<Ticket> {

    private PresentationDtoSummaryRenderer presentationDtoSummaryRenderer;

    public TicketRenderer(PresentationDtoSummaryRenderer presentationDtoSummaryRenderer) {
        this.presentationDtoSummaryRenderer = presentationDtoSummaryRenderer;
    }

    public String render(Ticket ticket) {
        return LINE_SEPARATOR +
                "Ticket Details" +
                HORIZONTAL_BAR +
                presentationDtoSummaryRenderer.render(ticket) +
                EMPTY_LINE +
                "Ticket's organization details" +
                HORIZONTAL_BAR + LINE_SEPARATOR +
                presentationDtoSummaryRenderer.render(ticket.getOrganization()) +
                EMPTY_LINE +
                "Submitter" +
                HORIZONTAL_BAR + LINE_SEPARATOR +
                presentationDtoSummaryRenderer.render(ticket.getSubmitter()) +
                EMPTY_LINE +
                "Assignee" +
                HORIZONTAL_BAR + LINE_SEPARATOR +
                presentationDtoSummaryRenderer.render(ticket.getAssignee());
    }

}
