package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Response projection for TNotificationAction
 */
public class TNotificationActionResponseProjection extends GraphQLResponseProjection {

    private final Map<String, Integer> projectionDepthOnFields = new HashMap<>();

    public TNotificationActionResponseProjection() {
    }

    public TNotificationActionResponseProjection(TNotificationActionResponseProjection projection) {
        super(projection);
    }

    public TNotificationActionResponseProjection(List<TNotificationActionResponseProjection> projections) {
        super(projections);
    }

    public TNotificationActionResponseProjection all$() {
        return all$(3);
    }

    public TNotificationActionResponseProjection all$(int maxDepth) {
        this.markAsUnread();
        this.markRead();
        this.typename();
        return this;
    }

    public TNotificationActionResponseProjection markAsUnread() {
        return markAsUnread(null);
    }

    public TNotificationActionResponseProjection markAsUnread(String alias) {
        add$(new GraphQLResponseField("markAsUnread").alias(alias));
        return this;
    }

    public TNotificationActionResponseProjection markRead() {
        return markRead(null);
    }

    public TNotificationActionResponseProjection markRead(String alias) {
        add$(new GraphQLResponseField("markRead").alias(alias));
        return this;
    }

    public TNotificationActionResponseProjection typename() {
        return typename(null);
    }

    public TNotificationActionResponseProjection typename(String alias) {
        add$(new GraphQLResponseField("__typename").alias(alias));
        return this;
    }

    @Override
    public TNotificationActionResponseProjection deepCopy$() {
        return new TNotificationActionResponseProjection(this);
    }


}
