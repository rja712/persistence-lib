package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.EmailAttachment;
import com.inboxintelligence.persistence.repository.EmailAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
