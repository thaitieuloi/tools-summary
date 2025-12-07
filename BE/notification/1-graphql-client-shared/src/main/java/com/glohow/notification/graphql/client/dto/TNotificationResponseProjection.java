package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Response projection for TNotification
 */
public class TNotificationResponseProjection extends GraphQLResponseProjection {

    private final Map<String, Integer> projectionDepthOnFields = new HashMap<>();

    public TNotificationResponseProjection() {
    }

    public TNotificationResponseProjection(TNotificationResponseProjection projection) {
        super(projection);
    }

    public TNotificationResponseProjection(List<TNotificationResponseProjection> projections) {
        super(projections);
    }

    public TNotificationResponseProjection all$() {
        return all$(3);
    }

    public TNotificationResponseProjection all$(int maxDepth) {
        this.action();
        if (projectionDepthOnFields.getOrDefault("TNotificationResponseProjection.TNotificationActionResponseProjection.actions", 0) <= maxDepth) {
            projectionDepthOnFields.put("TNotificationResponseProjection.TNotificationActionResponseProjection.actions", projectionDepthOnFields.getOrDefault("TNotificationResponseProjection.TNotificationActionResponseProjection.actions", 0) + 1);
            this.actions(new TNotificationActionResponseProjection().all$(maxDepth - projectionDepthOnFields.getOrDefault("TNotificationResponseProjection.TNotificationActionResponseProjection.actions", 0)));
        }
        this.channelId();
        this.content();
        this.createdAt();
        this.createdBy();
        this.groupToken();
        this.groupTokenTt();
        this.id();
        if (projectionDepthOnFields.getOrDefault("TNotificationResponseProjection.TNotificationMessageResponseProjection.message", 0) <= maxDepth) {
            projectionDepthOnFields.put("TNotificationResponseProjection.TNotificationMessageResponseProjection.message", projectionDepthOnFields.getOrDefault("TNotificationResponseProjection.TNotificationMessageResponseProjection.message", 0) + 1);
            this.message(new TNotificationMessageResponseProjection().all$(maxDepth - projectionDepthOnFields.getOrDefault("TNotificationResponseProjection.TNotificationMessageResponseProjection.message", 0)));
        }
        this.meta();
        this.sendEmail();
        this.sentSubscriberId();
        this.severity();
        this.status();
        this.type();
        this.updatedAt();
        this.updatedBy();
        this.vendor();
        this.typename();
        return this;
    }

    public TNotificationResponseProjection action() {
        return action(null);
    }

    public TNotificationResponseProjection action(String alias) {
        add$(new GraphQLResponseField("action").alias(alias));
        return this;
    }

    public TNotificationResponseProjection actions(TNotificationActionResponseProjection subProjection) {
        return actions(null, subProjection);
    }

    public TNotificationResponseProjection actions(String alias, TNotificationActionResponseProjection subProjection) {
        add$(new GraphQLResponseField("actions").alias(alias).projection(subProjection));
        return this;
    }

    public TNotificationResponseProjection channelId() {
        return channelId(null);
    }

    public TNotificationResponseProjection channelId(String alias) {
        add$(new GraphQLResponseField("channelId").alias(alias));
        return this;
    }

    public TNotificationResponseProjection content() {
        return content(null);
    }

    public TNotificationResponseProjection content(String alias) {
        add$(new GraphQLResponseField("content").alias(alias));
        return this;
    }

    public TNotificationResponseProjection createdAt() {
        return createdAt(null);
    }

    public TNotificationResponseProjection createdAt(String alias) {
        add$(new GraphQLResponseField("createdAt").alias(alias));
        return this;
    }

    public TNotificationResponseProjection createdBy() {
        return createdBy(null);
    }

    public TNotificationResponseProjection createdBy(String alias) {
        add$(new GraphQLResponseField("createdBy").alias(alias));
        return this;
    }

    public TNotificationResponseProjection groupToken() {
        return groupToken(null);
    }

    public TNotificationResponseProjection groupToken(String alias) {
        add$(new GraphQLResponseField("groupToken").alias(alias));
        return this;
    }

    public TNotificationResponseProjection groupTokenTt() {
        return groupTokenTt(null);
    }

    public TNotificationResponseProjection groupTokenTt(String alias) {
        add$(new GraphQLResponseField("groupTokenTt").alias(alias));
        return this;
    }

    public TNotificationResponseProjection id() {
        return id(null);
    }

    public TNotificationResponseProjection id(String alias) {
        add$(new GraphQLResponseField("id").alias(alias));
        return this;
    }

    public TNotificationResponseProjection message(TNotificationMessageResponseProjection subProjection) {
        return message(null, subProjection);
    }

    public TNotificationResponseProjection message(String alias, TNotificationMessageResponseProjection subProjection) {
        add$(new GraphQLResponseField("message").alias(alias).projection(subProjection));
        return this;
    }

    public TNotificationResponseProjection meta() {
        return meta(null);
    }

    public TNotificationResponseProjection meta(String alias) {
        add$(new GraphQLResponseField("meta").alias(alias));
        return this;
    }

    public TNotificationResponseProjection sendEmail() {
        return sendEmail(null);
    }

    public TNotificationResponseProjection sendEmail(String alias) {
        add$(new GraphQLResponseField("sendEmail").alias(alias));
        return this;
    }

    public TNotificationResponseProjection sentSubscriberId() {
        return sentSubscriberId(null);
    }

    public TNotificationResponseProjection sentSubscriberId(String alias) {
        add$(new GraphQLResponseField("sentSubscriberId").alias(alias));
        return this;
    }

    public TNotificationResponseProjection severity() {
        return severity(null);
    }

    public TNotificationResponseProjection severity(String alias) {
        add$(new GraphQLResponseField("severity").alias(alias));
        return this;
    }

    public TNotificationResponseProjection status() {
        return status(null);
    }

    public TNotificationResponseProjection status(String alias) {
        add$(new GraphQLResponseField("status").alias(alias));
        return this;
    }

    public TNotificationResponseProjection type() {
        return type(null);
    }

    public TNotificationResponseProjection type(String alias) {
        add$(new GraphQLResponseField("type").alias(alias));
        return this;
    }

    public TNotificationResponseProjection updatedAt() {
        return updatedAt(null);
    }

    public TNotificationResponseProjection updatedAt(String alias) {
        add$(new GraphQLResponseField("updatedAt").alias(alias));
        return this;
    }

    public TNotificationResponseProjection updatedBy() {
        return updatedBy(null);
    }

    public TNotificationResponseProjection updatedBy(String alias) {
        add$(new GraphQLResponseField("updatedBy").alias(alias));
        return this;
    }

    public TNotificationResponseProjection vendor() {
        return vendor(null);
    }

    public TNotificationResponseProjection vendor(String alias) {
        add$(new GraphQLResponseField("vendor").alias(alias));
        return this;
    }

    public TNotificationResponseProjection typename() {
        return typename(null);
    }

    public TNotificationResponseProjection typename(String alias) {
        add$(new GraphQLResponseField("__typename").alias(alias));
        return this;
    }

    @Override
    public TNotificationResponseProjection deepCopy$() {
        return new TNotificationResponseProjection(this);
    }


}
