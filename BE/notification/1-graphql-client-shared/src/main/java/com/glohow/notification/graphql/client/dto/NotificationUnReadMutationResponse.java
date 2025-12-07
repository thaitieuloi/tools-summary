package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class NotificationUnReadMutationResponse extends GraphQLResult<Map<String, Boolean>> {

    private static final String OPERATION_NAME = "notificationUnRead";

    public NotificationUnReadMutationResponse() {
    }

    public Boolean notificationUnRead() {
        Map<String, Boolean> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
