package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class UnreadNotImportantNotificationListQueryResponse extends GraphQLResult<Map<String, java.util.List<TNotification>>> {

    private static final String OPERATION_NAME = "unreadNotImportantNotificationList";

    public UnreadNotImportantNotificationListQueryResponse() {
    }

    public java.util.List<TNotification> unreadNotImportantNotificationList() {
        Map<String, java.util.List<TNotification>> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
