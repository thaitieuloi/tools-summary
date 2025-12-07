package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TChannelCreateInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public TChannelCreateInput() {
    }

    public TChannelCreateInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (name != null) {
            joiner.add("name: " + GraphQLRequestSerializer.getEntry(name));
        }
        return joiner.toString();
    }

    public static TChannelCreateInput.Builder builder() {
        return new TChannelCreateInput.Builder();
    }

    public static class Builder {

        private String name;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public TChannelCreateInput build() {
            return new TChannelCreateInput(name);
        }

    }
}
