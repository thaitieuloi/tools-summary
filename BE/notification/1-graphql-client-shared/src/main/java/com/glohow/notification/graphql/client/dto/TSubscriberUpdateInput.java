package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TSubscriberUpdateInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> _unset;
    private java.time.Instant lastEmailSentAt;
    private java.time.Instant lastScheduledAt;
    private String refToken;

    public TSubscriberUpdateInput() {
    }

    public TSubscriberUpdateInput(java.util.List<String> _unset, java.time.Instant lastEmailSentAt, java.time.Instant lastScheduledAt, String refToken) {
        this._unset = _unset;
        this.lastEmailSentAt = lastEmailSentAt;
        this.lastScheduledAt = lastScheduledAt;
        this.refToken = refToken;
    }

    public java.util.List<String> get_unset() {
        return _unset;
    }
    public void set_unset(java.util.List<String> _unset) {
        this._unset = _unset;
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

    public String getRefToken() {
        return refToken;
    }
    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (_unset != null) {
            joiner.add("_unset: " + GraphQLRequestSerializer.getEntry(_unset));
        }
        if (lastEmailSentAt != null) {
            joiner.add("lastEmailSentAt: " + GraphQLRequestSerializer.getEntry(lastEmailSentAt, true));
        }
        if (lastScheduledAt != null) {
            joiner.add("lastScheduledAt: " + GraphQLRequestSerializer.getEntry(lastScheduledAt, true));
        }
        if (refToken != null) {
            joiner.add("refToken: " + GraphQLRequestSerializer.getEntry(refToken));
        }
        return joiner.toString();
    }

    public static TSubscriberUpdateInput.Builder builder() {
        return new TSubscriberUpdateInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> _unset;
        private java.time.Instant lastEmailSentAt;
        private java.time.Instant lastScheduledAt;
        private String refToken;

        public Builder() {
        }

        public Builder set_unset(java.util.List<String> _unset) {
            this._unset = _unset;
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

        public Builder setRefToken(String refToken) {
            this.refToken = refToken;
            return this;
        }


        public TSubscriberUpdateInput build() {
            return new TSubscriberUpdateInput(_unset, lastEmailSentAt, lastScheduledAt, refToken);
        }

    }
}
