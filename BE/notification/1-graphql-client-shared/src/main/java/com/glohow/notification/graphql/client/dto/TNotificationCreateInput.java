package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationCreateInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> _unset;
    private String action;
    private String channel;
    private String content;
    private String entityType;
    private String groupToken;
    private java.time.Instant groupTokenTt;
    private String meta;
    private String senderAuthId;
    private String severity;
    private String type;
    private String vendor;

    public TNotificationCreateInput() {
    }

    public TNotificationCreateInput(java.util.List<String> _unset, String action, String channel, String content, String entityType, String groupToken, java.time.Instant groupTokenTt, String meta, String senderAuthId, String severity, String type, String vendor) {
        this._unset = _unset;
        this.action = action;
        this.channel = channel;
        this.content = content;
        this.entityType = entityType;
        this.groupToken = groupToken;
        this.groupTokenTt = groupTokenTt;
        this.meta = meta;
        this.senderAuthId = senderAuthId;
        this.severity = severity;
        this.type = type;
        this.vendor = vendor;
    }

    public java.util.List<String> get_unset() {
        return _unset;
    }
    public void set_unset(java.util.List<String> _unset) {
        this._unset = _unset;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getEntityType() {
        return entityType;
    }
    public void setEntityType(String entityType) {
        this.entityType = entityType;
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

    public String getMeta() {
        return meta;
    }
    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getSenderAuthId() {
        return senderAuthId;
    }
    public void setSenderAuthId(String senderAuthId) {
        this.senderAuthId = senderAuthId;
    }

    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
        if (_unset != null) {
            joiner.add("_unset: " + GraphQLRequestSerializer.getEntry(_unset));
        }
        if (action != null) {
            joiner.add("action: " + GraphQLRequestSerializer.getEntry(action));
        }
        if (channel != null) {
            joiner.add("channel: " + GraphQLRequestSerializer.getEntry(channel));
        }
        if (content != null) {
            joiner.add("content: " + GraphQLRequestSerializer.getEntry(content));
        }
        if (entityType != null) {
            joiner.add("entityType: " + GraphQLRequestSerializer.getEntry(entityType));
        }
        if (groupToken != null) {
            joiner.add("groupToken: " + GraphQLRequestSerializer.getEntry(groupToken));
        }
        if (groupTokenTt != null) {
            joiner.add("groupTokenTt: " + GraphQLRequestSerializer.getEntry(groupTokenTt, true));
        }
        if (meta != null) {
            joiner.add("meta: " + GraphQLRequestSerializer.getEntry(meta));
        }
        if (senderAuthId != null) {
            joiner.add("senderAuthId: " + GraphQLRequestSerializer.getEntry(senderAuthId));
        }
        if (severity != null) {
            joiner.add("severity: " + GraphQLRequestSerializer.getEntry(severity));
        }
        if (type != null) {
            joiner.add("type: " + GraphQLRequestSerializer.getEntry(type));
        }
        if (vendor != null) {
            joiner.add("vendor: " + GraphQLRequestSerializer.getEntry(vendor));
        }
        return joiner.toString();
    }

    public static TNotificationCreateInput.Builder builder() {
        return new TNotificationCreateInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> _unset;
        private String action;
        private String channel;
        private String content;
        private String entityType;
        private String groupToken;
        private java.time.Instant groupTokenTt;
        private String meta;
        private String senderAuthId;
        private String severity;
        private String type;
        private String vendor;

        public Builder() {
        }

        public Builder set_unset(java.util.List<String> _unset) {
            this._unset = _unset;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setEntityType(String entityType) {
            this.entityType = entityType;
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

        public Builder setMeta(String meta) {
            this.meta = meta;
            return this;
        }

        public Builder setSenderAuthId(String senderAuthId) {
            this.senderAuthId = senderAuthId;
            return this;
        }

        public Builder setSeverity(String severity) {
            this.severity = severity;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setVendor(String vendor) {
            this.vendor = vendor;
            return this;
        }


        public TNotificationCreateInput build() {
            return new TNotificationCreateInput(_unset, action, channel, content, entityType, groupToken, groupTokenTt, meta, senderAuthId, severity, type, vendor);
        }

    }
}
