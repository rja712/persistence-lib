package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.EmailAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailAttachmentRepository extends JpaRepository<EmailAttachment, Long> {

    List<EmailAttachment> findByEmailContentId(Long emailContentId);

    void deleteByEmailContentId(Long emailContentId);
}
