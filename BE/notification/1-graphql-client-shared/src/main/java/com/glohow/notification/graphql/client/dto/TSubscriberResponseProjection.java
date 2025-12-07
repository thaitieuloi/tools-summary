package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Response projection for TSubscriber
 */
public class TSubscriberResponseProjection extends GraphQLResponseProjection {

    private final Map<String, Integer> projectionDepthOnFields = new HashMap<>();

    public TSubscriberResponseProjection() {
    }

    public TSubscriberResponseProjection(TSubscriberResponseProjection projection) {
        super(projection);
    }

    public TSubscriberResponseProjection(List<TSubscriberResponseProjection> projections) {
        super(projections);
    }

    public TSubscriberResponseProjection all$() {
        return all$(3);
    }

    public TSubscriberResponseProjection all$(int maxDepth) {
        this.createdAt();
        this.createdBy();
        this.email();
        this.fullName();
        this.id();
        this.lastEmailSentAt();
        this.lastScheduledAt();
        this.refSource();
        this.refToken();
        this.updatedAt();
        this.updatedBy();
        this.typename();
        return this;
    }

    public TSubscriberResponseProjection createdAt() {
        return createdAt(null);
    }

    public TSubscriberResponseProjection createdAt(String alias) {
        add$(new GraphQLResponseField("createdAt").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection createdBy() {
        return createdBy(null);
    }

    public TSubscriberResponseProjection createdBy(String alias) {
        add$(new GraphQLResponseField("createdBy").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection email() {
        return email(null);
    }

    public TSubscriberResponseProjection email(String alias) {
        add$(new GraphQLResponseField("email").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection fullName() {
        return fullName(null);
    }

    public TSubscriberResponseProjection fullName(String alias) {
        add$(new GraphQLResponseField("fullName").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection id() {
        return id(null);
    }

    public TSubscriberResponseProjection id(String alias) {
        add$(new GraphQLResponseField("id").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection lastEmailSentAt() {
        return lastEmailSentAt(null);
    }

    public TSubscriberResponseProjection lastEmailSentAt(String alias) {
        add$(new GraphQLResponseField("lastEmailSentAt").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection lastScheduledAt() {
        return lastScheduledAt(null);
    }

    public TSubscriberResponseProjection lastScheduledAt(String alias) {
        add$(new GraphQLResponseField("lastScheduledAt").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection refSource() {
        return refSource(null);
    }

    public TSubscriberResponseProjection refSource(String alias) {
        add$(new GraphQLResponseField("refSource").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection refToken() {
        return refToken(null);
    }

    public TSubscriberResponseProjection refToken(String alias) {
        add$(new GraphQLResponseField("refToken").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection updatedAt() {
        return updatedAt(null);
    }

    public TSubscriberResponseProjection updatedAt(String alias) {
        add$(new GraphQLResponseField("updatedAt").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection updatedBy() {
        return updatedBy(null);
    }

    public TSubscriberResponseProjection updatedBy(String alias) {
        add$(new GraphQLResponseField("updatedBy").alias(alias));
        return this;
    }

    public TSubscriberResponseProjection typename() {
        return typename(null);
    }

    public TSubscriberResponseProjection typename(String alias) {
        add$(new GraphQLResponseField("__typename").alias(alias));
        return this;
    }

    @Override
    public TSubscriberResponseProjection deepCopy$() {
        return new TSubscriberResponseProjection(this);
    }


}
