package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationSummary implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String totalBookmarked;
    private String totalRead;
    private String totalUnRead;

    public TNotificationSummary() {
    }

    public TNotificationSummary(String totalBookmarked, String totalRead, String totalUnRead) {
        this.totalBookmarked = totalBookmarked;
        this.totalRead = totalRead;
        this.totalUnRead = totalUnRead;
    }

    public String getTotalBookmarked() {
        return totalBookmarked;
    }
    public void setTotalBookmarked(String totalBookmarked) {
        this.totalBookmarked = totalBookmarked;
    }

    public String getTotalRead() {
        return totalRead;
    }
    public void setTotalRead(String totalRead) {
        this.totalRead = totalRead;
    }

    public String getTotalUnRead() {
        return totalUnRead;
    }
    public void setTotalUnRead(String totalUnRead) {
        this.totalUnRead = totalUnRead;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (totalBookmarked != null) {
            joiner.add("totalBookmarked: " + GraphQLRequestSerializer.getEntry(totalBookmarked));
        }
        if (totalRead != null) {
            joiner.add("totalRead: " + GraphQLRequestSerializer.getEntry(totalRead));
        }
        if (totalUnRead != null) {
            joiner.add("totalUnRead: " + GraphQLRequestSerializer.getEntry(totalUnRead));
        }
        return joiner.toString();
    }

    public static TNotificationSummary.Builder builder() {
        return new TNotificationSummary.Builder();
    }

    public static class Builder {

        private String totalBookmarked;
        private String totalRead;
        private String totalUnRead;

        public Builder() {
        }

        public Builder setTotalBookmarked(String totalBookmarked) {
            this.totalBookmarked = totalBookmarked;
            return this;
        }

        public Builder setTotalRead(String totalRead) {
            this.totalRead = totalRead;
            return this;
        }

        public Builder setTotalUnRead(String totalUnRead) {
            this.totalUnRead = totalUnRead;
            return this;
        }


        public TNotificationSummary build() {
            return new TNotificationSummary(totalBookmarked, totalRead, totalUnRead);
        }

    }
}
