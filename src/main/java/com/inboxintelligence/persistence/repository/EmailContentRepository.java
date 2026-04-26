package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.ProcessedStatus;
import com.inboxintelligence.persistence.model.entity.EmailContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface EmailContentRepository extends JpaRepository<EmailContent, Long> {

    boolean existsByGmailMailboxIdAndMessageId(Long gmailMailboxId, String messageId);

    @Modifying
    @Query("""
            UPDATE EmailContent ec
            SET ec.processedStatus = :status,
                ec.processingNote  = :note,
                ec.updatedAt       = :now
            WHERE ec.id IN :ids
            """)
    void bulkUpdateStatusAndNote(
            @Param("ids")    List<Long> ids,
            @Param("status") ProcessedStatus status,
            @Param("note")   String note,
            @Param("now")    Instant now);
}
