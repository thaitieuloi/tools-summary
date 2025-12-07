package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class NotificationCreateMutationResponse extends GraphQLResult<Map<String, String>> {

    private static final String OPERATION_NAME = "notificationCreate";

    public NotificationCreateMutationResponse() {
    }

    public String notificationCreate() {
        Map<String, String> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
