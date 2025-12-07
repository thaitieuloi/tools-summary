package com.ttl.tool.notification.core.graphql;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * GraphQL API for Notification operations.
 * Basic implementation to demonstrate microservice structure.
 * 
 * Architecture:
 * GraphQL API → (Future: Command → Repository)
 * 
 * Example usage:
 * - Queries (getNotification, listNotifications)
 * - Mutations (notificationCreate, notificationDelete)
 */
@Component
@GraphQLApi
@RequiredArgsConstructor
public class NotificationGraphQLApi {

    /**
     * Query: Get a single notification by ID (placeholder)
     */
    @GraphQLQuery(description = "Get notification by ID")
    public NotificationDTO notificationFindById(@GraphQLArgument(name = "id") String id) {
        // Placeholder implementation
        return NotificationDTO.builder()
                .id(UUID.fromString(id))
                .title("Sample Notification")
                .message("This is a sample notification")
                .build();
    }

    /**
     * Query: List notifications (placeholder)
     */
    @GraphQLQuery(description = "List all notifications")
    public List<NotificationDTO> notificationList(
            @GraphQLArgument(name = "page") Integer page,
            @GraphQLArgument(name = "size") Integer size) {

        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;

        // Placeholder implementation
        List<NotificationDTO> notifications = new ArrayList<>();
        notifications.add(NotificationDTO.builder()
                .id(UUID.randomUUID())
                .title("Notification 1")
                .message("First notification message")
                .build());
        notifications.add(NotificationDTO.builder()
                .id(UUID.randomUUID())
                .title("Notification 2")
                .message("Second notification message")
                .build());

        return notifications;
    }

    /**
     * Mutation: Create a new notification (placeholder)
     * 
     * @return The ID of the created notification
     */
    @GraphQLMutation(name = "notificationCreate", description = "Create a new notification")
    public UUID notificationCreate(
            @GraphQLArgument(name = "title") String title,
            @GraphQLArgument(name = "message") String message) {

        // Placeholder implementation
        UUID newId = UUID.randomUUID();
        System.out.println("Created notification: " + title + " with ID: " + newId);
        return newId;
    }

    /**
     * Mutation: Delete a notification (placeholder)
     * 
     * @return true if deletion was successful
     */
    @GraphQLMutation(description = "Delete a notification")
    public Boolean notificationDelete(@GraphQLArgument(name = "id") String id) {
        // Placeholder implementation
        System.out.println("Deleted notification with ID: " + id);
        return true;
    }

    /**
     * Simple DTO for Notification
     */
    @lombok.Data
    @lombok.Builder
    public static class NotificationDTO {
        private UUID id;
        private String title;
        private String message;
    }
}
