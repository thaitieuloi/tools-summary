package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TPersonalNotificationWithSubscriberListInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> excludeSubscriberAuthIds;
    private com.glohow.common.core.search.Filters filters;
    private String keyword;
    private java.util.List<java.util.List<String>> orderBy;
    private Integer page;
    private Integer pageSize = 20;

    public TPersonalNotificationWithSubscriberListInput() {
    }

    public TPersonalNotificationWithSubscriberListInput(java.util.List<String> excludeSubscriberAuthIds, com.glohow.common.core.search.Filters filters, String keyword, java.util.List<java.util.List<String>> orderBy, Integer page, Integer pageSize) {
        this.excludeSubscriberAuthIds = excludeSubscriberAuthIds;
        this.filters = filters;
        this.keyword = keyword;
        this.orderBy = orderBy;
        this.page = page;
        this.pageSize = pageSize;
    }

    public java.util.List<String> getExcludeSubscriberAuthIds() {
        return excludeSubscriberAuthIds;
    }
    public void setExcludeSubscriberAuthIds(java.util.List<String> excludeSubscriberAuthIds) {
        this.excludeSubscriberAuthIds = excludeSubscriberAuthIds;
    }

    public com.glohow.common.core.search.Filters getFilters() {
        return filters;
    }
    public void setFilters(com.glohow.common.core.search.Filters filters) {
        this.filters = filters;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public java.util.List<java.util.List<String>> getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(java.util.List<java.util.List<String>> orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (excludeSubscriberAuthIds != null) {
            joiner.add("excludeSubscriberAuthIds: " + GraphQLRequestSerializer.getEntry(excludeSubscriberAuthIds));
        }
        if (filters != null) {
            joiner.add("filters: " + GraphQLRequestSerializer.getEntry(filters, true));
        }
        if (keyword != null) {
            joiner.add("keyword: " + GraphQLRequestSerializer.getEntry(keyword));
        }
        if (orderBy != null) {
            joiner.add("orderBy: " + GraphQLRequestSerializer.getEntry(orderBy));
        }
        if (page != null) {
            joiner.add("page: " + GraphQLRequestSerializer.getEntry(page));
        }
        if (pageSize != null) {
            joiner.add("pageSize: " + GraphQLRequestSerializer.getEntry(pageSize));
        }
        return joiner.toString();
    }

    public static TPersonalNotificationWithSubscriberListInput.Builder builder() {
        return new TPersonalNotificationWithSubscriberListInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> excludeSubscriberAuthIds;
        private com.glohow.common.core.search.Filters filters;
        private String keyword;
        private java.util.List<java.util.List<String>> orderBy;
        private Integer page;
        private Integer pageSize = 20;

        public Builder() {
        }

        public Builder setExcludeSubscriberAuthIds(java.util.List<String> excludeSubscriberAuthIds) {
            this.excludeSubscriberAuthIds = excludeSubscriberAuthIds;
            return this;
        }

        public Builder setFilters(com.glohow.common.core.search.Filters filters) {
            this.filters = filters;
            return this;
        }

        public Builder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder setOrderBy(java.util.List<java.util.List<String>> orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder setPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }


        public TPersonalNotificationWithSubscriberListInput build() {
            return new TPersonalNotificationWithSubscriberListInput(excludeSubscriberAuthIds, filters, keyword, orderBy, page, pageSize);
        }

    }
}
