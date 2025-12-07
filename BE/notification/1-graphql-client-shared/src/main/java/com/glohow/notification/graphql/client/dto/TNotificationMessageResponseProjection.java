package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Response projection for TNotificationMessage
 */
public class TNotificationMessageResponseProjection extends GraphQLResponseProjection {

    private final Map<String, Integer> projectionDepthOnFields = new HashMap<>();

    public TNotificationMessageResponseProjection() {
    }

    public TNotificationMessageResponseProjection(TNotificationMessageResponseProjection projection) {
        super(projection);
    }

    public TNotificationMessageResponseProjection(List<TNotificationMessageResponseProjection> projections) {
        super(projections);
    }

    public TNotificationMessageResponseProjection all$() {
        return all$(3);
    }

    public TNotificationMessageResponseProjection all$(int maxDepth) {
        this.bookmark();
        this.createdAt();
        this.createdBy();
        this.id();
        this.notificationId();
        this.readAt();
        this.receivedAt();
        this.severity();
        this.subscriberId();
        this.updatedAt();
        this.updatedBy();
        this.typename();
        return this;
    }

    public TNotificationMessageResponseProjection bookmark() {
        return bookmark(null);
    }

    public TNotificationMessageResponseProjection bookmark(String alias) {
        add$(new GraphQLResponseField("bookmark").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection createdAt() {
        return createdAt(null);
    }

    public TNotificationMessageResponseProjection createdAt(String alias) {
        add$(new GraphQLResponseField("createdAt").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection createdBy() {
        return createdBy(null);
    }

    public TNotificationMessageResponseProjection createdBy(String alias) {
        add$(new GraphQLResponseField("createdBy").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection id() {
        return id(null);
    }

    public TNotificationMessageResponseProjection id(String alias) {
        add$(new GraphQLResponseField("id").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection notificationId() {
        return notificationId(null);
    }

    public TNotificationMessageResponseProjection notificationId(String alias) {
        add$(new GraphQLResponseField("notificationId").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection readAt() {
        return readAt(null);
    }

    public TNotificationMessageResponseProjection readAt(String alias) {
        add$(new GraphQLResponseField("readAt").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection receivedAt() {
        return receivedAt(null);
    }

    public TNotificationMessageResponseProjection receivedAt(String alias) {
        add$(new GraphQLResponseField("receivedAt").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection severity() {
        return severity(null);
    }

    public TNotificationMessageResponseProjection severity(String alias) {
        add$(new GraphQLResponseField("severity").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection subscriberId() {
        return subscriberId(null);
    }

    public TNotificationMessageResponseProjection subscriberId(String alias) {
        add$(new GraphQLResponseField("subscriberId").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection updatedAt() {
        return updatedAt(null);
    }

    public TNotificationMessageResponseProjection updatedAt(String alias) {
        add$(new GraphQLResponseField("updatedAt").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection updatedBy() {
        return updatedBy(null);
    }

    public TNotificationMessageResponseProjection updatedBy(String alias) {
        add$(new GraphQLResponseField("updatedBy").alias(alias));
        return this;
    }

    public TNotificationMessageResponseProjection typename() {
        return typename(null);
    }

    public TNotificationMessageResponseProjection typename(String alias) {
        add$(new GraphQLResponseField("__typename").alias(alias));
        return this;
    }

    @Override
    public TNotificationMessageResponseProjection deepCopy$() {
        return new TNotificationMessageResponseProjection(this);
    }


}
