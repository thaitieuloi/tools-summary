package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationBookmarkInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int bookmark;
    private String id;

    public TNotificationBookmarkInput() {
    }

    public TNotificationBookmarkInput(int bookmark, String id) {
        this.bookmark = bookmark;
        this.id = id;
    }

    public int getBookmark() {
        return bookmark;
    }
    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        joiner.add("bookmark: " + GraphQLRequestSerializer.getEntry(bookmark));
        if (id != null) {
            joiner.add("id: " + GraphQLRequestSerializer.getEntry(id));
        }
        return joiner.toString();
    }

    public static TNotificationBookmarkInput.Builder builder() {
        return new TNotificationBookmarkInput.Builder();
    }

    public static class Builder {

        private int bookmark;
        private String id;

        public Builder() {
        }

        public Builder setBookmark(int bookmark) {
            this.bookmark = bookmark;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }


        public TNotificationBookmarkInput build() {
            return new TNotificationBookmarkInput(bookmark, id);
        }

    }
}
