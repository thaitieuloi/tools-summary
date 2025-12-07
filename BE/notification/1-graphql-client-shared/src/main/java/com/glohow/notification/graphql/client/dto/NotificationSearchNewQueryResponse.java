package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class NotificationSearchNewQueryResponse extends GraphQLResult<Map<String, java.util.List<TNotification>>> {

    private static final String OPERATION_NAME = "notificationSearchNew";

    public NotificationSearchNewQueryResponse() {
    }

    public java.util.List<TNotification> notificationSearchNew() {
        Map<String, java.util.List<TNotification>> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
