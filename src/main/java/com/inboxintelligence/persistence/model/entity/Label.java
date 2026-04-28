package com.inboxintelligence.persistence.model.entity;

import com.inboxintelligence.persistence.model.LabelSource;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(
        name = "label",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_label_mailbox_full_name",
                        columnNames = {"fk_gmail_mailbox_id", "full_name"}
                )
        },
        indexes = {
                @Index(name = "idx_label_mailbox", columnList = "fk_gmail_mailbox_id"),
                @Index(name = "idx_label_source",  columnList = "source"),
                @Index(name = "idx_label_active",  columnList = "is_active")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fk_gmail_mailbox_id", nullable = false)
    private Long gmailMailboxId;

    @Column(name = "display_name", nullable = false, length = 255)
    private String displayName;

    // full path e.g. "Finance/Tax/2024"
    @Column(name = "full_name", nullable = false, length = 1024)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, length = 16)
    private LabelSource source;

    @Column(name = "description")
    private String description;

    // nullable — USER labels may not have one; SYSTEM labels always do
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    @Column(name = "reference_embedding")
    private float[] referenceEmbedding;

    // cosine similarity confidence from LLM; null for USER labels
    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

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
