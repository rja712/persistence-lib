package com.inboxintelligence.persistence.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "cluster_label",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_cluster_label",
                        columnNames = {"fk_cluster_id"}
                )
        },
        indexes = {
                @Index(name = "idx_cluster_label_cluster", columnList = "fk_cluster_id"),
                @Index(name = "idx_cluster_label_label",   columnList = "fk_label_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fk_cluster_id", nullable = false, unique = true)
    private Long clusterId;

    @Column(name = "fk_label_id", nullable = false)
    private Long labelId;

    // cosine similarity between cluster centroid and label reference_embedding
    @Column(name = "mapping_score")
    private Double mappingScore;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
