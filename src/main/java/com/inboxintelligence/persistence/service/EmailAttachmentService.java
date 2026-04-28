package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.EmailAttachment;
import com.inboxintelligence.persistence.repository.EmailAttachmentRepository;
import com.inboxintelligence.persistence.storage.EmailStorageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailAttachmentService {

    private final EmailAttachmentRepository repository;

    public EmailAttachment save(EmailAttachment attachment) {
        return repository.save(attachment);
    }

    public List<EmailAttachment> findByEmailContentId(Long emailContentId) {
        return repository.findByEmailContentId(emailContentId);
    }

    public void deleteAllByEmailContentId(Long emailContentId, EmailStorageProvider storageProvider) {
        List<EmailAttachment> attachments = repository.findByEmailContentId(emailContentId);
        for (EmailAttachment attachment : attachments) {
            if (StringUtils.hasText(attachment.getStoragePath())) {
                storageProvider.deleteContent(attachment.getStoragePath());
            }
        }
        repository.deleteByEmailContentId(emailContentId);
    }
}
