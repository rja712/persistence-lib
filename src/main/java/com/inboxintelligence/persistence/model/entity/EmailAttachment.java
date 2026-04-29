package com.inboxintelligence.persistence.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "email_attachment",
        indexes = {
                @Index(name = "idx_email_attachment_email_content", columnList = "fk_email_content_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fk_email_content_id", nullable = false)
    private Long emailContentId;

    @Column(name = "email_attachment_id", length = 1024)
    private String emailAttachmentId;

    @Column(name = "file_name", nullable = false, length = 1024)
    private String fileName;

    @Column(name = "mime_type", length = 255)
    private String mimeType;

    @Column(name = "size_in_bytes")
    private Long sizeInBytes;

    @Column(name = "storage_path", length = 1024)
    private String storagePath;

    @Column(name = "storage_provider", nullable = false, length = 64)
    @Builder.Default
    private String storageProvider = "local";

    @Column(name = "content_id", length = 1024)
    private String contentId;

    @Column(name = "is_inline", nullable = false)
    @Builder.Default
    private Boolean isInline = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
