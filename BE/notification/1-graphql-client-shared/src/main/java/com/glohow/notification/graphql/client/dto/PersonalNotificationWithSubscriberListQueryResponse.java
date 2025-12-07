package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResult;
import java.util.Map;

public class PersonalNotificationWithSubscriberListQueryResponse extends GraphQLResult<Map<String, java.util.List<TNotificationWithSubscriber>>> {

    private static final String OPERATION_NAME = "personalNotificationWithSubscriberList";

    public PersonalNotificationWithSubscriberListQueryResponse() {
    }

    public java.util.List<TNotificationWithSubscriber> personalNotificationWithSubscriberList() {
        Map<String, java.util.List<TNotificationWithSubscriber>> data = getData();
        return data != null ? data.get(OPERATION_NAME) : null;
    }

}
