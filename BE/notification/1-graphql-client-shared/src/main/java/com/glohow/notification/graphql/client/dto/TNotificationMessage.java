package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationMessage implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer bookmark;
    private java.time.Instant createdAt;
    private String createdBy;
    private String id;
    private String notificationId;
    private java.time.Instant readAt;
    private java.time.Instant receivedAt;
    private String severity;
    private String subscriberId;
    private java.time.Instant updatedAt;
    private String updatedBy;

    public TNotificationMessage() {
    }

    public TNotificationMessage(Integer bookmark, java.time.Instant createdAt, String createdBy, String id, String notificationId, java.time.Instant readAt, java.time.Instant receivedAt, String severity, String subscriberId, java.time.Instant updatedAt, String updatedBy) {
        this.bookmark = bookmark;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.id = id;
        this.notificationId = notificationId;
        this.readAt = readAt;
        this.receivedAt = receivedAt;
        this.severity = severity;
        this.subscriberId = subscriberId;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Integer getBookmark() {
        return bookmark;
    }
    public void setBookmark(Integer bookmark) {
        this.bookmark = bookmark;
    }

    public java.time.Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.time.Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public java.time.Instant getReadAt() {
        return readAt;
    }
    public void setReadAt(java.time.Instant readAt) {
        this.readAt = readAt;
    }

    public java.time.Instant getReceivedAt() {
        return receivedAt;
    }
    public void setReceivedAt(java.time.Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSubscriberId() {
        return subscriberId;
    }
    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public java.time.Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.time.Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (bookmark != null) {
            joiner.add("bookmark: " + GraphQLRequestSerializer.getEntry(bookmark));
        }
        if (createdAt != null) {
            joiner.add("createdAt: " + GraphQLRequestSerializer.getEntry(createdAt, true));
        }
        if (createdBy != null) {
            joiner.add("createdBy: " + GraphQLRequestSerializer.getEntry(createdBy));
        }
        if (id != null) {
            joiner.add("id: " + GraphQLRequestSerializer.getEntry(id));
        }
        if (notificationId != null) {
            joiner.add("notificationId: " + GraphQLRequestSerializer.getEntry(notificationId));
        }
        if (readAt != null) {
            joiner.add("readAt: " + GraphQLRequestSerializer.getEntry(readAt, true));
        }
        if (receivedAt != null) {
            joiner.add("receivedAt: " + GraphQLRequestSerializer.getEntry(receivedAt, true));
        }
        if (severity != null) {
            joiner.add("severity: " + GraphQLRequestSerializer.getEntry(severity));
        }
        if (subscriberId != null) {
            joiner.add("subscriberId: " + GraphQLRequestSerializer.getEntry(subscriberId));
        }
        if (updatedAt != null) {
            joiner.add("updatedAt: " + GraphQLRequestSerializer.getEntry(updatedAt, true));
        }
        if (updatedBy != null) {
            joiner.add("updatedBy: " + GraphQLRequestSerializer.getEntry(updatedBy));
        }
        return joiner.toString();
    }

    public static TNotificationMessage.Builder builder() {
        return new TNotificationMessage.Builder();
    }

    public static class Builder {

        private Integer bookmark;
        private java.time.Instant createdAt;
        private String createdBy;
        private String id;
        private String notificationId;
        private java.time.Instant readAt;
        private java.time.Instant receivedAt;
        private String severity;
        private String subscriberId;
        private java.time.Instant updatedAt;
        private String updatedBy;

        public Builder() {
        }

        public Builder setBookmark(Integer bookmark) {
            this.bookmark = bookmark;
            return this;
        }

        public Builder setCreatedAt(java.time.Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setReadAt(java.time.Instant readAt) {
            this.readAt = readAt;
            return this;
        }

        public Builder setReceivedAt(java.time.Instant receivedAt) {
            this.receivedAt = receivedAt;
            return this;
        }

        public Builder setSeverity(String severity) {
            this.severity = severity;
            return this;
        }

        public Builder setSubscriberId(String subscriberId) {
            this.subscriberId = subscriberId;
            return this;
        }

        public Builder setUpdatedAt(java.time.Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }


        public TNotificationMessage build() {
            return new TNotificationMessage(bookmark, createdAt, createdBy, id, notificationId, readAt, receivedAt, severity, subscriberId, updatedAt, updatedBy);
        }

    }
}
