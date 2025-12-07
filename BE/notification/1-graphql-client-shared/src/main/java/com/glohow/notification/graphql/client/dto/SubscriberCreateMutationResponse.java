package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class SubscriberCreateMutationResponse extends GraphQLResult<Map<String, String>> {

    private static final String OPERATION_NAME = "subscriberCreate";

    public SubscriberCreateMutationResponse() {
    }

    public String subscriberCreate() {
        Map<String, String> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
