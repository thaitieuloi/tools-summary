package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TChannelSubscriptionBatchCreateByChannelInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> _unset;
    private String channel;
    private java.util.List<String> subscriberAuthIds;

    public TChannelSubscriptionBatchCreateByChannelInput() {
    }

    public TChannelSubscriptionBatchCreateByChannelInput(java.util.List<String> _unset, String channel, java.util.List<String> subscriberAuthIds) {
        this._unset = _unset;
        this.channel = channel;
        this.subscriberAuthIds = subscriberAuthIds;
    }

    public java.util.List<String> get_unset() {
        return _unset;
    }
    public void set_unset(java.util.List<String> _unset) {
        this._unset = _unset;
    }

    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public java.util.List<String> getSubscriberAuthIds() {
        return subscriberAuthIds;
    }
    public void setSubscriberAuthIds(java.util.List<String> subscriberAuthIds) {
        this.subscriberAuthIds = subscriberAuthIds;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (_unset != null) {
            joiner.add("_unset: " + GraphQLRequestSerializer.getEntry(_unset));
        }
        if (channel != null) {
            joiner.add("channel: " + GraphQLRequestSerializer.getEntry(channel));
        }
        if (subscriberAuthIds != null) {
            joiner.add("subscriberAuthIds: " + GraphQLRequestSerializer.getEntry(subscriberAuthIds));
        }
        return joiner.toString();
    }

    public static TChannelSubscriptionBatchCreateByChannelInput.Builder builder() {
        return new TChannelSubscriptionBatchCreateByChannelInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> _unset;
        private String channel;
        private java.util.List<String> subscriberAuthIds;

        public Builder() {
        }

        public Builder set_unset(java.util.List<String> _unset) {
            this._unset = _unset;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setSubscriberAuthIds(java.util.List<String> subscriberAuthIds) {
            this.subscriberAuthIds = subscriberAuthIds;
            return this;
        }


        public TChannelSubscriptionBatchCreateByChannelInput build() {
            return new TChannelSubscriptionBatchCreateByChannelInput(_unset, channel, subscriberAuthIds);
        }

    }
}
