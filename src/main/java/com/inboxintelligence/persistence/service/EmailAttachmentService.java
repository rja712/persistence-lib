package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.EmailAttachment;
import com.inboxintelligence.persistence.repository.EmailAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAttachmentService {

    private final EmailAttachmentRepository repository;

    public EmailAttachment save(EmailAttachment attachment) {
        return repository.save(attachment);
    }
}
