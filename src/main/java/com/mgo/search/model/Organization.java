package com.mgo.search.model;

import java.util.Date;
import java.util.Set;

public class Organization {

    private long id;
    private String url;
    private String externalId;
    private String name;
    private Set<String> domainNames;
    private Date createdAt;
    private String details;
    private boolean sharedTickets;
    private Set<String> tags;

}
