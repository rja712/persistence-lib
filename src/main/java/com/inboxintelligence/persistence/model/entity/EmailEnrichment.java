package com.inboxintelligence.persistence.model.entity;

import com.inboxintelligence.persistence.model.ClusterAssignmentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(
        name = "email_enrichments",
        indexes = {
                @Index(name = "idx_email_enrichments_email_content", columnList = "fk_email_content_id"),
                @Index(name = "idx_email_enrichments_cluster", columnList = "fk_cluster_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailEnrichment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fk_email_content_id", nullable = false, unique = true)
    private Long emailContentId;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    @Column(name = "embedding")
    private float[] embedding;

    @Column(name = "embedding_model", length = 64)
    private String embeddingModel;

    @Column(name = "fk_cluster_id")
    private Long clusterId;

    @Column(name = "cluster_probability")
    private Double clusterProbability;

    @Enumerated(EnumType.STRING)
    @Column(name = "cluster_assignment_type", length = 16)
    private ClusterAssignmentType clusterAssignmentType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
