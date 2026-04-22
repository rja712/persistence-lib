package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.EmailEnrichment;
import com.inboxintelligence.persistence.repository.EmailEnrichmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailEnrichmentService {

    private final EmailEnrichmentRepository repository;

    public EmailEnrichment save(EmailEnrichment enrichment) {
        return repository.save(enrichment);
    }

    public Optional<EmailEnrichment> findByEmailContentId(Long emailContentId) {
        return repository.findByEmailContentId(emailContentId);
    }
}
