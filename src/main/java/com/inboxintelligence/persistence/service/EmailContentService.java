package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.ProcessedStatus;
import com.inboxintelligence.persistence.model.entity.EmailContent;
import com.inboxintelligence.persistence.repository.EmailContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailContentService {

    private final EmailContentRepository repository;

    public boolean existsByGmailMailboxIdAndMessageId(Long id, String messageId) {
        return repository.existsByGmailMailboxIdAndMessageId(id, messageId);
    }

    public List<EmailContent> findAll() {
        return repository.findAll();
    }

    public Optional<EmailContent> findById(Long id) {
        return repository.findById(id);
    }

    public EmailContent save(EmailContent emailContent) {
        return repository.save(emailContent);
    }

    public EmailContent updateProcessedStatus(EmailContent emailContent, ProcessedStatus status) {
        emailContent.setProcessedStatus(status);
        return repository.save(emailContent);
    }

    public EmailContent updateProcessingNote(EmailContent emailContent, String note) {
        emailContent.setProcessingNote(note);
        return repository.save(emailContent);
    }
}
