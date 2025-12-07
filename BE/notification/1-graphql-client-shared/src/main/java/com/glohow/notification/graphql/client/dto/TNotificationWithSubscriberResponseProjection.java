package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Response projection for TNotificationWithSubscriber
 */
public class TNotificationWithSubscriberResponseProjection extends GraphQLResponseProjection {

    private final Map<String, Integer> projectionDepthOnFields = new HashMap<>();

    public TNotificationWithSubscriberResponseProjection() {
    }

    public TNotificationWithSubscriberResponseProjection(TNotificationWithSubscriberResponseProjection projection) {
        super(projection);
    }

    public TNotificationWithSubscriberResponseProjection(List<TNotificationWithSubscriberResponseProjection> projections) {
        super(projections);
    }

    public TNotificationWithSubscriberResponseProjection all$() {
        return all$(3);
    }

    public TNotificationWithSubscriberResponseProjection all$(int maxDepth) {
        if (projectionDepthOnFields.getOrDefault("TNotificationWithSubscriberResponseProjection.TNotificationResponseProjection.notification", 0) <= maxDepth) {
            projectionDepthOnFields.put("TNotificationWithSubscriberResponseProjection.TNotificationResponseProjection.notification", projectionDepthOnFields.getOrDefault("TNotificationWithSubscriberResponseProjection.TNotificationResponseProjection.notification", 0) + 1);
            this.notification(new TNotificationResponseProjection().all$(maxDepth - projectionDepthOnFields.getOrDefault("TNotificationWithSubscriberResponseProjection.TNotificationResponseProjection.notification", 0)));
        }
        if (projectionDepthOnFields.getOrDefault("TNotificationWithSubscriberResponseProjection.TSubscriberResponseProjection.subscriber", 0) <= maxDepth) {
            projectionDepthOnFields.put("TNotificationWithSubscriberResponseProjection.TSubscriberResponseProjection.subscriber", projectionDepthOnFields.getOrDefault("TNotificationWithSubscriberResponseProjection.TSubscriberResponseProjection.subscriber", 0) + 1);
            this.subscriber(new TSubscriberResponseProjection().all$(maxDepth - projectionDepthOnFields.getOrDefault("TNotificationWithSubscriberResponseProjection.TSubscriberResponseProjection.subscriber", 0)));
        }
        this.typename();
        return this;
    }

    public TNotificationWithSubscriberResponseProjection notification(TNotificationResponseProjection subProjection) {
        return notification(null, subProjection);
    }

    public TNotificationWithSubscriberResponseProjection notification(String alias, TNotificationResponseProjection subProjection) {
        add$(new GraphQLResponseField("notification").alias(alias).projection(subProjection));
        return this;
    }

    public TNotificationWithSubscriberResponseProjection subscriber(TSubscriberResponseProjection subProjection) {
        return subscriber(null, subProjection);
    }

    public TNotificationWithSubscriberResponseProjection subscriber(String alias, TSubscriberResponseProjection subProjection) {
        add$(new GraphQLResponseField("subscriber").alias(alias).projection(subProjection));
        return this;
    }

    public TNotificationWithSubscriberResponseProjection typename() {
        return typename(null);
    }

    public TNotificationWithSubscriberResponseProjection typename(String alias) {
        add$(new GraphQLResponseField("__typename").alias(alias));
        return this;
    }

    @Override
    public TNotificationWithSubscriberResponseProjection deepCopy$() {
        return new TNotificationWithSubscriberResponseProjection(this);
    }


}
