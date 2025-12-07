package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TChannelSubscriptionBatchDeleteByChannelInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String channel;
    private java.util.List<String> subscriberAuthIds;

    public TChannelSubscriptionBatchDeleteByChannelInput() {
    }

    public TChannelSubscriptionBatchDeleteByChannelInput(String channel, java.util.List<String> subscriberAuthIds) {
        this.channel = channel;
        this.subscriberAuthIds = subscriberAuthIds;
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
        if (channel != null) {
            joiner.add("channel: " + GraphQLRequestSerializer.getEntry(channel));
        }
        if (subscriberAuthIds != null) {
            joiner.add("subscriberAuthIds: " + GraphQLRequestSerializer.getEntry(subscriberAuthIds));
        }
        return joiner.toString();
    }

    public static TChannelSubscriptionBatchDeleteByChannelInput.Builder builder() {
        return new TChannelSubscriptionBatchDeleteByChannelInput.Builder();
    }

    public static class Builder {

        private String channel;
        private java.util.List<String> subscriberAuthIds;

        public Builder() {
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setSubscriberAuthIds(java.util.List<String> subscriberAuthIds) {
            this.subscriberAuthIds = subscriberAuthIds;
            return this;
        }


        public TChannelSubscriptionBatchDeleteByChannelInput build() {
            return new TChannelSubscriptionBatchDeleteByChannelInput(channel, subscriberAuthIds);
        }

    }
}
