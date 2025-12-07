package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TEmailNotificationSearchInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String refToken;

    public TEmailNotificationSearchInput() {
    }

    public TEmailNotificationSearchInput(String refToken) {
        this.refToken = refToken;
    }

    public String getRefToken() {
        return refToken;
    }
    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (refToken != null) {
            joiner.add("refToken: " + GraphQLRequestSerializer.getEntry(refToken));
        }
        return joiner.toString();
    }

    public static TEmailNotificationSearchInput.Builder builder() {
        return new TEmailNotificationSearchInput.Builder();
    }

    public static class Builder {

        private String refToken;

        public Builder() {
        }

        public Builder setRefToken(String refToken) {
            this.refToken = refToken;
            return this;
        }


        public TEmailNotificationSearchInput build() {
            return new TEmailNotificationSearchInput(refToken);
        }

    }
}
