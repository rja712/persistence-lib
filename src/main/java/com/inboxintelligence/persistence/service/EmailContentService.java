package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.ProcessedStatus;
import com.inboxintelligence.persistence.model.entity.EmailContent;
import com.inboxintelligence.persistence.repository.EmailContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailContentService {

    private final EmailContentRepository repository;

    @Transactional(readOnly = true)
    public boolean existsByGmailMailboxIdAndMessageId(Long gmailMailboxId, String messageId) {
        return repository.existsByGmailMailboxIdAndMessageId(gmailMailboxId, messageId);
    }

    @Transactional(readOnly = true)
    public List<EmailContent> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EmailContent> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public EmailContent save(EmailContent emailContent) {
        return repository.save(emailContent);
    }

    @Transactional
    public EmailContent updateStatusAndNote(EmailContent emailContent, ProcessedStatus status, String note) {
        emailContent.setProcessedStatus(status);
        emailContent.setProcessingNote(note);
        return repository.save(emailContent);
    }

    @Transactional
    public void bulkUpdateStatusAndNote(List<Long> emailContentIds, ProcessedStatus status, String note) {
        repository.bulkUpdateStatusAndNote(emailContentIds, status, note, Instant.now());
    }
}
