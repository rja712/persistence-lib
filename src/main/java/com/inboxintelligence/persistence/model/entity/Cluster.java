package com.inboxintelligence.persistence.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(
        name = "cluster",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_cluster_mailbox_label",
                        columnNames = {"fk_gmail_mailbox_id", "cluster_label"}
                )
        },
        indexes = {
                @Index(name = "idx_cluster_mailbox", columnList = "fk_gmail_mailbox_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fk_gmail_mailbox_id", nullable = false)
    private Long gmailMailboxId;

    @Column(name = "cluster_label", nullable = false)
    private Integer clusterLabel;

    @Column(name = "email_count", nullable = false)
    private Integer emailCount;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    @Column(name = "centroid", nullable = false)
    private float[] centroid;

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
