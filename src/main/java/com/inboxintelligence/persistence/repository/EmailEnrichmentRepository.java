package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.EmailEnrichment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailEnrichmentRepository extends JpaRepository<EmailEnrichment, Long> {

    Optional<EmailEnrichment> findByEmailContentId(Long emailContentId);
}
