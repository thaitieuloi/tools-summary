package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TChannelSubscriptionCreateInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String channel;
    private String subscriberAuthId;

    public TChannelSubscriptionCreateInput() {
    }

    public TChannelSubscriptionCreateInput(String channel, String subscriberAuthId) {
        this.channel = channel;
        this.subscriberAuthId = subscriberAuthId;
    }

    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSubscriberAuthId() {
        return subscriberAuthId;
    }
    public void setSubscriberAuthId(String subscriberAuthId) {
        this.subscriberAuthId = subscriberAuthId;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (channel != null) {
            joiner.add("channel: " + GraphQLRequestSerializer.getEntry(channel));
        }
        if (subscriberAuthId != null) {
            joiner.add("subscriberAuthId: " + GraphQLRequestSerializer.getEntry(subscriberAuthId));
        }
        return joiner.toString();
    }

    public static TChannelSubscriptionCreateInput.Builder builder() {
        return new TChannelSubscriptionCreateInput.Builder();
    }

    public static class Builder {

        private String channel;
        private String subscriberAuthId;

        public Builder() {
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setSubscriberAuthId(String subscriberAuthId) {
            this.subscriberAuthId = subscriberAuthId;
            return this;
        }


        public TChannelSubscriptionCreateInput build() {
            return new TChannelSubscriptionCreateInput(channel, subscriberAuthId);
        }

    }
}
