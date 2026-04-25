package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.EmailEnrichment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmailEnrichmentRepository extends JpaRepository<EmailEnrichment, Long> {

    Optional<EmailEnrichment> findByEmailContentId(Long emailContentId);

    @Query("SELECT ee FROM EmailEnrichment ee JOIN EmailContent ec ON ee.emailContentId = ec.id WHERE ec.gmailMailboxId = :gmailMailboxId")
    List<EmailEnrichment> findByGmailMailboxId(Long gmailMailboxId);
}
