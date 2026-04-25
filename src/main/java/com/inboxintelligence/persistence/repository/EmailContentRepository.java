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

}
