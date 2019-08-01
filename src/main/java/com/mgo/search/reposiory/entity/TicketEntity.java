package com.mgo.search.reposiory.entity;

import com.google.gson.annotations.SerializedName;
import com.mgo.search.model.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;
import java.util.Set;

public class TicketEntity implements Entity {

    @SerializedName("_id")
    private String id;
    private String url;
    @SerializedName("external_id")
    private String externalId;
    @SerializedName("created_at")
    private String createdAt;
    private String type;
    private String subject;
    private String description;
    private String priority;
    private String status;
    @SerializedName("submitter_id")
    private String submitterId;
    @SerializedName("assignee_id")
    private String assigneeId;
    @SerializedName("organization_id")
    private String organizationId;
    private Set<String> tags;
    @SerializedName("has_incidents")
    private boolean hasIncidents;
    @SerializedName("due_at")
    private String dueAt;
    private String via;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assignee) {
        this.assigneeId = assignee;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organization) {
        this.organizationId = organization;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public boolean hasIncidents() {
        return hasIncidents;
    }

    public void setHasIncidents(boolean hasIncidents) {
        this.hasIncidents = hasIncidents;
    }

    public String getDueAt() {
        return dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
