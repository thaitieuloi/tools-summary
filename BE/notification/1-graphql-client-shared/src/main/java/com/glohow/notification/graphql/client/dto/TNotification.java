package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotification implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String action;
    private TNotificationAction actions;
    private String channelId;
    private String content;
    private java.time.Instant createdAt;
    private String createdBy;
    private String groupToken;
    private java.time.Instant groupTokenTt;
    private String id;
    private TNotificationMessage message;
    private String meta;
    private Boolean sendEmail;
    private String sentSubscriberId;
    private String severity;
    private String status;
    private String type;
    private java.time.Instant updatedAt;
    private String updatedBy;
    private String vendor;

    public TNotification() {
    }

    public TNotification(String action, TNotificationAction actions, String channelId, String content, java.time.Instant createdAt, String createdBy, String groupToken, java.time.Instant groupTokenTt, String id, TNotificationMessage message, String meta, Boolean sendEmail, String sentSubscriberId, String severity, String status, String type, java.time.Instant updatedAt, String updatedBy, String vendor) {
        this.action = action;
        this.actions = actions;
        this.channelId = channelId;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.groupToken = groupToken;
        this.groupTokenTt = groupTokenTt;
        this.id = id;
        this.message = message;
        this.meta = meta;
        this.sendEmail = sendEmail;
        this.sentSubscriberId = sentSubscriberId;
        this.severity = severity;
        this.status = status;
        this.type = type;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.vendor = vendor;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public TNotificationAction getActions() {
        return actions;
    }
    public void setActions(TNotificationAction actions) {
        this.actions = actions;
    }

    public String getChannelId() {
        return channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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

    public String getGroupToken() {
        return groupToken;
    }
    public void setGroupToken(String groupToken) {
        this.groupToken = groupToken;
    }

    public java.time.Instant getGroupTokenTt() {
        return groupTokenTt;
    }
    public void setGroupTokenTt(java.time.Instant groupTokenTt) {
        this.groupTokenTt = groupTokenTt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public TNotificationMessage getMessage() {
        return message;
    }
    public void setMessage(TNotificationMessage message) {
        this.message = message;
    }

    public String getMeta() {
        return meta;
    }
    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }
    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getSentSubscriberId() {
        return sentSubscriberId;
    }
    public void setSentSubscriberId(String sentSubscriberId) {
        this.sentSubscriberId = sentSubscriberId;
    }

    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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

    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (action != null) {
            joiner.add("action: " + GraphQLRequestSerializer.getEntry(action));
        }
        if (actions != null) {
            joiner.add("actions: " + GraphQLRequestSerializer.getEntry(actions));
        }
        if (channelId != null) {
            joiner.add("channelId: " + GraphQLRequestSerializer.getEntry(channelId));
        }
        if (content != null) {
            joiner.add("content: " + GraphQLRequestSerializer.getEntry(content));
        }
        if (createdAt != null) {
            joiner.add("createdAt: " + GraphQLRequestSerializer.getEntry(createdAt, true));
        }
        if (createdBy != null) {
            joiner.add("createdBy: " + GraphQLRequestSerializer.getEntry(createdBy));
        }
        if (groupToken != null) {
            joiner.add("groupToken: " + GraphQLRequestSerializer.getEntry(groupToken));
        }
        if (groupTokenTt != null) {
            joiner.add("groupTokenTt: " + GraphQLRequestSerializer.getEntry(groupTokenTt, true));
        }
        if (id != null) {
            joiner.add("id: " + GraphQLRequestSerializer.getEntry(id));
        }
        if (message != null) {
            joiner.add("message: " + GraphQLRequestSerializer.getEntry(message));
        }
        if (meta != null) {
            joiner.add("meta: " + GraphQLRequestSerializer.getEntry(meta));
        }
        if (sendEmail != null) {
            joiner.add("sendEmail: " + GraphQLRequestSerializer.getEntry(sendEmail));
        }
        if (sentSubscriberId != null) {
            joiner.add("sentSubscriberId: " + GraphQLRequestSerializer.getEntry(sentSubscriberId));
        }
        if (severity != null) {
            joiner.add("severity: " + GraphQLRequestSerializer.getEntry(severity));
        }
        if (status != null) {
            joiner.add("status: " + GraphQLRequestSerializer.getEntry(status));
        }
        if (type != null) {
            joiner.add("type: " + GraphQLRequestSerializer.getEntry(type));
        }
        if (updatedAt != null) {
            joiner.add("updatedAt: " + GraphQLRequestSerializer.getEntry(updatedAt, true));
        }
        if (updatedBy != null) {
            joiner.add("updatedBy: " + GraphQLRequestSerializer.getEntry(updatedBy));
        }
        if (vendor != null) {
            joiner.add("vendor: " + GraphQLRequestSerializer.getEntry(vendor));
        }
        return joiner.toString();
    }

    public static TNotification.Builder builder() {
        return new TNotification.Builder();
    }

    public static class Builder {

        private String action;
        private TNotificationAction actions;
        private String channelId;
        private String content;
        private java.time.Instant createdAt;
        private String createdBy;
        private String groupToken;
        private java.time.Instant groupTokenTt;
        private String id;
        private TNotificationMessage message;
        private String meta;
        private Boolean sendEmail;
        private String sentSubscriberId;
        private String severity;
        private String status;
        private String type;
        private java.time.Instant updatedAt;
        private String updatedBy;
        private String vendor;

        public Builder() {
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setActions(TNotificationAction actions) {
            this.actions = actions;
            return this;
        }

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
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

        public Builder setGroupToken(String groupToken) {
            this.groupToken = groupToken;
            return this;
        }

        public Builder setGroupTokenTt(java.time.Instant groupTokenTt) {
            this.groupTokenTt = groupTokenTt;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setMessage(TNotificationMessage message) {
            this.message = message;
            return this;
        }

        public Builder setMeta(String meta) {
            this.meta = meta;
            return this;
        }

        public Builder setSendEmail(Boolean sendEmail) {
            this.sendEmail = sendEmail;
            return this;
        }

        public Builder setSentSubscriberId(String sentSubscriberId) {
            this.sentSubscriberId = sentSubscriberId;
            return this;
        }

        public Builder setSeverity(String severity) {
            this.severity = severity;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
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

        public Builder setVendor(String vendor) {
            this.vendor = vendor;
            return this;
        }


        public TNotification build() {
            return new TNotification(action, actions, channelId, content, createdAt, createdBy, groupToken, groupTokenTt, id, message, meta, sendEmail, sentSubscriberId, severity, status, type, updatedAt, updatedBy, vendor);
        }

    }
}
