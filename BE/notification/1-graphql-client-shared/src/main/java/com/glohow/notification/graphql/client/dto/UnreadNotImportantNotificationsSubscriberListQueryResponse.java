package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class UnreadNotImportantNotificationsSubscriberListQueryResponse extends GraphQLResult<Map<String, java.util.List<TSubscriber>>> {

    private static final String OPERATION_NAME = "unreadNotImportantNotificationsSubscriberList";

    public UnreadNotImportantNotificationsSubscriberListQueryResponse() {
    }

    public java.util.List<TSubscriber> unreadNotImportantNotificationsSubscriberList() {
        Map<String, java.util.List<TSubscriber>> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
