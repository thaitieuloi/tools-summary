package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class ChannelCreateMutationResponse extends GraphQLResult<Map<String, String>> {

    private static final String OPERATION_NAME = "channelCreate";

    public ChannelCreateMutationResponse() {
    }

    public String channelCreate() {
        Map<String, String> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
