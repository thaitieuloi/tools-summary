package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TNotificationSearchInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String filters;
    private String keyword;
    private String lastNotificationId;
    private String mode;
    private java.util.List<java.util.List<String>> orderBy;
    private Integer page;
    private Integer pageSize = 20;

    public TNotificationSearchInput() {
    }

    public TNotificationSearchInput(String filters, String keyword, String lastNotificationId, String mode, java.util.List<java.util.List<String>> orderBy, Integer page, Integer pageSize) {
        this.filters = filters;
        this.keyword = keyword;
        this.lastNotificationId = lastNotificationId;
        this.mode = mode;
        this.orderBy = orderBy;
        this.page = page;
        this.pageSize = pageSize;
    }

    public String getFilters() {
        return filters;
    }
    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLastNotificationId() {
        return lastNotificationId;
    }
    public void setLastNotificationId(String lastNotificationId) {
        this.lastNotificationId = lastNotificationId;
    }

    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
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
        if (filters != null) {
            joiner.add("filters: " + GraphQLRequestSerializer.getEntry(filters));
        }
        if (keyword != null) {
            joiner.add("keyword: " + GraphQLRequestSerializer.getEntry(keyword));
        }
        if (lastNotificationId != null) {
            joiner.add("lastNotificationId: " + GraphQLRequestSerializer.getEntry(lastNotificationId));
        }
        if (mode != null) {
            joiner.add("mode: " + GraphQLRequestSerializer.getEntry(mode));
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

    public static TNotificationSearchInput.Builder builder() {
        return new TNotificationSearchInput.Builder();
    }

    public static class Builder {

        private String filters;
        private String keyword;
        private String lastNotificationId;
        private String mode;
        private java.util.List<java.util.List<String>> orderBy;
        private Integer page;
        private Integer pageSize = 20;

        public Builder() {
        }

        public Builder setFilters(String filters) {
            this.filters = filters;
            return this;
        }

        public Builder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder setLastNotificationId(String lastNotificationId) {
            this.lastNotificationId = lastNotificationId;
            return this;
        }

        public Builder setMode(String mode) {
            this.mode = mode;
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


        public TNotificationSearchInput build() {
            return new TNotificationSearchInput(filters, keyword, lastNotificationId, mode, orderBy, page, pageSize);
        }

    }
}
