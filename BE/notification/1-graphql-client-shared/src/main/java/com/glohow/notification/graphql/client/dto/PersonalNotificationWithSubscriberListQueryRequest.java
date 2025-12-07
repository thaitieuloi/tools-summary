package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperation;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperationRequest;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PersonalNotificationWithSubscriberListQueryRequest implements GraphQLOperationRequest {

    public static final String OPERATION_NAME = "personalNotificationWithSubscriberList";
    public static final GraphQLOperation OPERATION_TYPE = GraphQLOperation.QUERY;

    private String alias;
    private final Map<String, Object> input = new LinkedHashMap<>();
    private final Set<String> useObjectMapperForInputSerialization = new HashSet<>();

    public PersonalNotificationWithSubscriberListQueryRequest() {
    }

    public PersonalNotificationWithSubscriberListQueryRequest(String alias) {
        this.alias = alias;
    }

    public void setInput(TPersonalNotificationWithSubscriberListInput input) {
        this.input.put("input", input);
    }

    @Override
    public GraphQLOperation getOperationType() {
        return OPERATION_TYPE;
    }

    @Override
    public String getOperationName() {
        return OPERATION_NAME;
    }

    @Override
    public String getAlias() {
        return alias != null ? alias : OPERATION_NAME;
    }

    @Override
    public Map<String, Object> getInput() {
        return input;
    }

    @Override
    public Set<String> getUseObjectMapperForInputSerialization() {
        return useObjectMapperForInputSerialization;
    }

    @Override
    public String toString() {
        return Objects.toString(input);
    }

    public static PersonalNotificationWithSubscriberListQueryRequest.Builder builder() {
        return new PersonalNotificationWithSubscriberListQueryRequest.Builder();
    }

    public static class Builder {

        private String $alias;
        private TPersonalNotificationWithSubscriberListInput input;

        public Builder() {
        }

        public Builder alias(String alias) {
            this.$alias = alias;
            return this;
        }

        public Builder setInput(TPersonalNotificationWithSubscriberListInput input) {
            this.input = input;
            return this;
        }


        public PersonalNotificationWithSubscriberListQueryRequest build() {
            PersonalNotificationWithSubscriberListQueryRequest obj = new PersonalNotificationWithSubscriberListQueryRequest($alias);
            obj.setInput(input);
            return obj;
        }

    }
}
