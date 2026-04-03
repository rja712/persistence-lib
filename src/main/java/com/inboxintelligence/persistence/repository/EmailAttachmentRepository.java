package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.EmailAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAttachmentRepository extends JpaRepository<EmailAttachment, Long> {

}
