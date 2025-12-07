package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperation;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperationRequest;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ChannelSubscriptionBatchCreateByChannelMutationRequest implements GraphQLOperationRequest {

    public static final String OPERATION_NAME = "channelSubscriptionBatchCreateByChannel";
    public static final GraphQLOperation OPERATION_TYPE = GraphQLOperation.MUTATION;

    private String alias;
    private final Map<String, Object> input = new LinkedHashMap<>();
    private final Set<String> useObjectMapperForInputSerialization = new HashSet<>();

    public ChannelSubscriptionBatchCreateByChannelMutationRequest() {
    }

    public ChannelSubscriptionBatchCreateByChannelMutationRequest(String alias) {
        this.alias = alias;
    }

    public void setInput(TChannelSubscriptionBatchCreateByChannelInput input) {
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

    public static ChannelSubscriptionBatchCreateByChannelMutationRequest.Builder builder() {
        return new ChannelSubscriptionBatchCreateByChannelMutationRequest.Builder();
    }

    public static class Builder {

        private String $alias;
        private TChannelSubscriptionBatchCreateByChannelInput input;

        public Builder() {
        }

        public Builder alias(String alias) {
            this.$alias = alias;
            return this;
        }

        public Builder setInput(TChannelSubscriptionBatchCreateByChannelInput input) {
            this.input = input;
            return this;
        }


        public ChannelSubscriptionBatchCreateByChannelMutationRequest build() {
            ChannelSubscriptionBatchCreateByChannelMutationRequest obj = new ChannelSubscriptionBatchCreateByChannelMutationRequest($alias);
            obj.setInput(input);
            return obj;
        }

    }
}
