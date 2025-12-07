package com.ttl.tool.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project entity representing a project in the system.
 * 
 * Features:
 * - Basic project information (name, description, status)
 * - URN field dynamically built as "tools:project:{id}"
 * - Audit fields (createdAt, updatedAt, createdBy, updatedBy)
 * - Active flag for soft delete
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, INACTIVE, COMPLETED, ARCHIVED

    @Transient
    private String urn;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    /**
     * Build URN dynamically as "tools:project:{id}"
     * This method is called automatically after entity is loaded from database
     */
    @PostLoad
    @PostPersist
    @PostUpdate
    public void buildUrn() {
        if (this.id != null) {
            this.urn = "tools:project:" + this.id;
        }
    }
}
