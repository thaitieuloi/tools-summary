package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationAction implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean markAsUnread;
    private Boolean markRead;

    public TNotificationAction() {
    }

    public TNotificationAction(Boolean markAsUnread, Boolean markRead) {
        this.markAsUnread = markAsUnread;
        this.markRead = markRead;
    }

    public Boolean getMarkAsUnread() {
        return markAsUnread;
    }
    public void setMarkAsUnread(Boolean markAsUnread) {
        this.markAsUnread = markAsUnread;
    }

    public Boolean getMarkRead() {
        return markRead;
    }
    public void setMarkRead(Boolean markRead) {
        this.markRead = markRead;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (markAsUnread != null) {
            joiner.add("markAsUnread: " + GraphQLRequestSerializer.getEntry(markAsUnread));
        }
        if (markRead != null) {
            joiner.add("markRead: " + GraphQLRequestSerializer.getEntry(markRead));
        }
        return joiner.toString();
    }

    public static TNotificationAction.Builder builder() {
        return new TNotificationAction.Builder();
    }

    public static class Builder {

        private Boolean markAsUnread;
        private Boolean markRead;

        public Builder() {
        }

        public Builder setMarkAsUnread(Boolean markAsUnread) {
            this.markAsUnread = markAsUnread;
            return this;
        }

        public Builder setMarkRead(Boolean markRead) {
            this.markRead = markRead;
            return this;
        }


        public TNotificationAction build() {
            return new TNotificationAction(markAsUnread, markRead);
        }

    }
}
