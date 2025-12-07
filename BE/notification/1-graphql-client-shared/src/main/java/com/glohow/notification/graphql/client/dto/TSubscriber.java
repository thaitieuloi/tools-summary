package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TSubscriber implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.time.Instant createdAt;
    private String createdBy;
    private String email;
    private String fullName;
    private String id;
    private java.time.Instant lastEmailSentAt;
    private java.time.Instant lastScheduledAt;
    private String refSource;
    private String refToken;
    private java.time.Instant updatedAt;
    private String updatedBy;

    public TSubscriber() {
    }

    public TSubscriber(java.time.Instant createdAt, String createdBy, String email, String fullName, String id, java.time.Instant lastEmailSentAt, java.time.Instant lastScheduledAt, String refSource, String refToken, java.time.Instant updatedAt, String updatedBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.email = email;
        this.fullName = fullName;
        this.id = id;
        this.lastEmailSentAt = lastEmailSentAt;
        this.lastScheduledAt = lastScheduledAt;
        this.refSource = refSource;
        this.refToken = refToken;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public java.time.Instant getLastEmailSentAt() {
        return lastEmailSentAt;
    }
    public void setLastEmailSentAt(java.time.Instant lastEmailSentAt) {
        this.lastEmailSentAt = lastEmailSentAt;
    }

    public java.time.Instant getLastScheduledAt() {
        return lastScheduledAt;
    }
    public void setLastScheduledAt(java.time.Instant lastScheduledAt) {
        this.lastScheduledAt = lastScheduledAt;
    }

    public String getRefSource() {
        return refSource;
    }
    public void setRefSource(String refSource) {
        this.refSource = refSource;
    }

    public String getRefToken() {
        return refToken;
    }
    public void setRefToken(String refToken) {
        this.refToken = refToken;
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
        if (createdAt != null) {
            joiner.add("createdAt: " + GraphQLRequestSerializer.getEntry(createdAt, true));
        }
        if (createdBy != null) {
            joiner.add("createdBy: " + GraphQLRequestSerializer.getEntry(createdBy));
        }
        if (email != null) {
            joiner.add("email: " + GraphQLRequestSerializer.getEntry(email));
        }
        if (fullName != null) {
            joiner.add("fullName: " + GraphQLRequestSerializer.getEntry(fullName));
        }
        if (id != null) {
            joiner.add("id: " + GraphQLRequestSerializer.getEntry(id));
        }
        if (lastEmailSentAt != null) {
            joiner.add("lastEmailSentAt: " + GraphQLRequestSerializer.getEntry(lastEmailSentAt, true));
        }
        if (lastScheduledAt != null) {
            joiner.add("lastScheduledAt: " + GraphQLRequestSerializer.getEntry(lastScheduledAt, true));
        }
        if (refSource != null) {
            joiner.add("refSource: " + GraphQLRequestSerializer.getEntry(refSource));
        }
        if (refToken != null) {
            joiner.add("refToken: " + GraphQLRequestSerializer.getEntry(refToken));
        }
        if (updatedAt != null) {
            joiner.add("updatedAt: " + GraphQLRequestSerializer.getEntry(updatedAt, true));
        }
        if (updatedBy != null) {
            joiner.add("updatedBy: " + GraphQLRequestSerializer.getEntry(updatedBy));
        }
        return joiner.toString();
    }

    public static TSubscriber.Builder builder() {
        return new TSubscriber.Builder();
    }

    public static class Builder {

        private java.time.Instant createdAt;
        private String createdBy;
        private String email;
        private String fullName;
        private String id;
        private java.time.Instant lastEmailSentAt;
        private java.time.Instant lastScheduledAt;
        private String refSource;
        private String refToken;
        private java.time.Instant updatedAt;
        private String updatedBy;

        public Builder() {
        }

        public Builder setCreatedAt(java.time.Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setLastEmailSentAt(java.time.Instant lastEmailSentAt) {
            this.lastEmailSentAt = lastEmailSentAt;
            return this;
        }

        public Builder setLastScheduledAt(java.time.Instant lastScheduledAt) {
            this.lastScheduledAt = lastScheduledAt;
            return this;
        }

        public Builder setRefSource(String refSource) {
            this.refSource = refSource;
            return this;
        }

        public Builder setRefToken(String refToken) {
            this.refToken = refToken;
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


        public TSubscriber build() {
            return new TSubscriber(createdAt, createdBy, email, fullName, id, lastEmailSentAt, lastScheduledAt, refSource, refToken, updatedAt, updatedBy);
        }

    }
}
