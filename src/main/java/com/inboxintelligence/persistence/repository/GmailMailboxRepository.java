package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.GmailMailbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GmailMailboxRepository extends JpaRepository<GmailMailbox, Long> {

    Optional<GmailMailbox> findByEmailAddress(String emailAddress);

}
