package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperation;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLOperationRequest;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ChannelSubscriptionBatchDeleteByChannelMutationRequest implements GraphQLOperationRequest {

    public static final String OPERATION_NAME = "channelSubscriptionBatchDeleteByChannel";
    public static final GraphQLOperation OPERATION_TYPE = GraphQLOperation.MUTATION;

    private String alias;
    private final Map<String, Object> input = new LinkedHashMap<>();
    private final Set<String> useObjectMapperForInputSerialization = new HashSet<>();

    public ChannelSubscriptionBatchDeleteByChannelMutationRequest() {
    }

    public ChannelSubscriptionBatchDeleteByChannelMutationRequest(String alias) {
        this.alias = alias;
    }

    public void setInput(TChannelSubscriptionBatchDeleteByChannelInput input) {
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

    public static ChannelSubscriptionBatchDeleteByChannelMutationRequest.Builder builder() {
        return new ChannelSubscriptionBatchDeleteByChannelMutationRequest.Builder();
    }

    public static class Builder {

        private String $alias;
        private TChannelSubscriptionBatchDeleteByChannelInput input;

        public Builder() {
        }

        public Builder alias(String alias) {
            this.$alias = alias;
            return this;
        }

        public Builder setInput(TChannelSubscriptionBatchDeleteByChannelInput input) {
            this.input = input;
            return this;
        }


        public ChannelSubscriptionBatchDeleteByChannelMutationRequest build() {
            ChannelSubscriptionBatchDeleteByChannelMutationRequest obj = new ChannelSubscriptionBatchDeleteByChannelMutationRequest($alias);
            obj.setInput(input);
            return obj;
        }

    }
}
