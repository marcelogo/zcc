package com.mgo.search.model;

import java.util.Date;
import java.util.Set;

public class Ticket {

    private String id;
    private String url;
    private String externalId;
    private Date createdAt;
    private TicketType type;
    private String subject;
    private String description;
    private TicketPriority priority;
    private TicketStatus status;
    private User submitter;
    private User assignee;
    private Organization organization;
    private Set<String> tags;
    private boolean hasIncidents;
    private TicketChannel channel;

}
