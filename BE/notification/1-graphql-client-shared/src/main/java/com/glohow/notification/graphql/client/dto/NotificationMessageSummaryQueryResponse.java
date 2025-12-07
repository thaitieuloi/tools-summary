package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class NotificationMessageSummaryQueryResponse extends GraphQLResult<Map<String, TNotificationSummary>> {

    private static final String OPERATION_NAME = "notificationMessageSummary";

    public NotificationMessageSummaryQueryResponse() {
    }

    public TNotificationSummary notificationMessageSummary() {
        Map<String, TNotificationSummary> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
