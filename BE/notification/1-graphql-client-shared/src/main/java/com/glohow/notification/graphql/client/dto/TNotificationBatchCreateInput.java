package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationBatchCreateInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> _unset;
    private String action;
    private String content;
    private String groupToken;
    private java.time.Instant groupTokenTt;
    private String meta;
    private String senderAuthId;
    private java.util.List<TTargetChannel> targetChannels;
    private String type;
    private String vendor;

    public TNotificationBatchCreateInput() {
    }

    public TNotificationBatchCreateInput(java.util.List<String> _unset, String action, String content, String groupToken, java.time.Instant groupTokenTt, String meta, String senderAuthId, java.util.List<TTargetChannel> targetChannels, String type, String vendor) {
        this._unset = _unset;
        this.action = action;
        this.content = content;
        this.groupToken = groupToken;
        this.groupTokenTt = groupTokenTt;
        this.meta = meta;
        this.senderAuthId = senderAuthId;
        this.targetChannels = targetChannels;
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

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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

    public java.util.List<TTargetChannel> getTargetChannels() {
        return targetChannels;
    }
    public void setTargetChannels(java.util.List<TTargetChannel> targetChannels) {
        this.targetChannels = targetChannels;
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
        if (content != null) {
            joiner.add("content: " + GraphQLRequestSerializer.getEntry(content));
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
        if (targetChannels != null) {
            joiner.add("targetChannels: " + GraphQLRequestSerializer.getEntry(targetChannels));
        }
        if (type != null) {
            joiner.add("type: " + GraphQLRequestSerializer.getEntry(type));
        }
        if (vendor != null) {
            joiner.add("vendor: " + GraphQLRequestSerializer.getEntry(vendor));
        }
        return joiner.toString();
    }

    public static TNotificationBatchCreateInput.Builder builder() {
        return new TNotificationBatchCreateInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> _unset;
        private String action;
        private String content;
        private String groupToken;
        private java.time.Instant groupTokenTt;
        private String meta;
        private String senderAuthId;
        private java.util.List<TTargetChannel> targetChannels;
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

        public Builder setContent(String content) {
            this.content = content;
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

        public Builder setTargetChannels(java.util.List<TTargetChannel> targetChannels) {
            this.targetChannels = targetChannels;
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


        public TNotificationBatchCreateInput build() {
            return new TNotificationBatchCreateInput(_unset, action, content, groupToken, groupTokenTt, meta, senderAuthId, targetChannels, type, vendor);
        }

    }
}
