package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationWithSubscriber implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private TNotification notification;
    private TSubscriber subscriber;

    public TNotificationWithSubscriber() {
    }

    public TNotificationWithSubscriber(TNotification notification, TSubscriber subscriber) {
        this.notification = notification;
        this.subscriber = subscriber;
    }

    public TNotification getNotification() {
        return notification;
    }
    public void setNotification(TNotification notification) {
        this.notification = notification;
    }

    public TSubscriber getSubscriber() {
        return subscriber;
    }
    public void setSubscriber(TSubscriber subscriber) {
        this.subscriber = subscriber;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (notification != null) {
            joiner.add("notification: " + GraphQLRequestSerializer.getEntry(notification));
        }
        if (subscriber != null) {
            joiner.add("subscriber: " + GraphQLRequestSerializer.getEntry(subscriber));
        }
        return joiner.toString();
    }

    public static TNotificationWithSubscriber.Builder builder() {
        return new TNotificationWithSubscriber.Builder();
    }

    public static class Builder {

        private TNotification notification;
        private TSubscriber subscriber;

        public Builder() {
        }

        public Builder setNotification(TNotification notification) {
            this.notification = notification;
            return this;
        }

        public Builder setSubscriber(TSubscriber subscriber) {
            this.subscriber = subscriber;
            return this;
        }


        public TNotificationWithSubscriber build() {
            return new TNotificationWithSubscriber(notification, subscriber);
        }

    }
}
