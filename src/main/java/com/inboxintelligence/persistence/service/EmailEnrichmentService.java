package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.ClusterAssignmentType;
import com.inboxintelligence.persistence.model.EmailEmbeddingProjection;
import com.inboxintelligence.persistence.model.entity.EmailEnrichment;
import com.inboxintelligence.persistence.repository.EmailEnrichmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailEnrichmentService {

    private final EmailEnrichmentRepository repository;

    @Transactional
    public EmailEnrichment save(EmailEnrichment enrichment) {
        return repository.save(enrichment);
    }

    @Transactional(readOnly = true)
    public Optional<EmailEnrichment> findByEmailContentId(Long emailContentId) {
        return repository.findByEmailContentId(emailContentId);
    }

    @Transactional
    public List<EmailEnrichment> saveAll(List<EmailEnrichment> enrichments) {
        return repository.saveAll(enrichments);
    }

    @Transactional(readOnly = true)
    public List<EmailEmbeddingProjection> findIdAndEmbeddingByGmailMailboxId(Long mailboxId) {
        return repository.findIdAndEmbeddingByGmailMailboxId(mailboxId);
    }

    @Transactional
    public void bulkAssignCluster(List<Long> ids, Long clusterId, ClusterAssignmentType type) {
        repository.bulkAssignCluster(ids, clusterId, type, Instant.now());
    }
}
