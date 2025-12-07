package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class NotificationBatchCreateMutationResponse extends GraphQLResult<Map<String, java.util.List<String>>> {

    private static final String OPERATION_NAME = "notificationBatchCreate";

    public NotificationBatchCreateMutationResponse() {
    }

    public java.util.List<String> notificationBatchCreate() {
        Map<String, java.util.List<String>> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
