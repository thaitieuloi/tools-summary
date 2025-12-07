package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationReadInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> ids;

    public TNotificationReadInput() {
    }

    public TNotificationReadInput(java.util.List<String> ids) {
        this.ids = ids;
    }

    public java.util.List<String> getIds() {
        return ids;
    }
    public void setIds(java.util.List<String> ids) {
        this.ids = ids;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (ids != null) {
            joiner.add("ids: " + GraphQLRequestSerializer.getEntry(ids));
        }
        return joiner.toString();
    }

    public static TNotificationReadInput.Builder builder() {
        return new TNotificationReadInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> ids;

        public Builder() {
        }

        public Builder setIds(java.util.List<String> ids) {
            this.ids = ids;
            return this;
        }


        public TNotificationReadInput build() {
            return new TNotificationReadInput(ids);
        }

    }
}
