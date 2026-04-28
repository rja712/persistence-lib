package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.LabelSource;
import com.inboxintelligence.persistence.model.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findByGmailMailboxId(Long gmailMailboxId);

    List<Label> findByGmailMailboxIdAndIsActiveTrue(Long gmailMailboxId);

    List<Label> findByGmailMailboxIdAndSource(Long gmailMailboxId, LabelSource source);

    Optional<Label> findByGmailMailboxIdAndFullName(Long gmailMailboxId, String fullName);
}
