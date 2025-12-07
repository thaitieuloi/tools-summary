package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseField;
import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLResponseProjection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Response projection for TNotificationSummary
 */
public class TNotificationSummaryResponseProjection extends GraphQLResponseProjection {

    private final Map<String, Integer> projectionDepthOnFields = new HashMap<>();

    public TNotificationSummaryResponseProjection() {
    }

    public TNotificationSummaryResponseProjection(TNotificationSummaryResponseProjection projection) {
        super(projection);
    }

    public TNotificationSummaryResponseProjection(List<TNotificationSummaryResponseProjection> projections) {
        super(projections);
    }

    public TNotificationSummaryResponseProjection all$() {
        return all$(3);
    }

    public TNotificationSummaryResponseProjection all$(int maxDepth) {
        this.totalBookmarked();
        this.totalRead();
        this.totalUnRead();
        this.typename();
        return this;
    }

    public TNotificationSummaryResponseProjection totalBookmarked() {
        return totalBookmarked(null);
    }

    public TNotificationSummaryResponseProjection totalBookmarked(String alias) {
        add$(new GraphQLResponseField("totalBookmarked").alias(alias));
        return this;
    }

    public TNotificationSummaryResponseProjection totalRead() {
        return totalRead(null);
    }

    public TNotificationSummaryResponseProjection totalRead(String alias) {
        add$(new GraphQLResponseField("totalRead").alias(alias));
        return this;
    }

    public TNotificationSummaryResponseProjection totalUnRead() {
        return totalUnRead(null);
    }

    public TNotificationSummaryResponseProjection totalUnRead(String alias) {
        add$(new GraphQLResponseField("totalUnRead").alias(alias));
        return this;
    }

    public TNotificationSummaryResponseProjection typename() {
        return typename(null);
    }

    public TNotificationSummaryResponseProjection typename(String alias) {
        add$(new GraphQLResponseField("__typename").alias(alias));
        return this;
    }

    @Override
    public TNotificationSummaryResponseProjection deepCopy$() {
        return new TNotificationSummaryResponseProjection(this);
    }


}
