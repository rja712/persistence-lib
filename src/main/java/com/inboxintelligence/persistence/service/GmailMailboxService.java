package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.GmailMailbox;
import com.inboxintelligence.persistence.repository.GmailMailboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GmailMailboxService {

    private final GmailMailboxRepository repository;

    public GmailMailbox save(GmailMailbox gmailMailbox) {
        return repository.save(gmailMailbox);
    }

    public Optional<GmailMailbox> findByEmailAddress(String email) {
        return repository.findByEmailAddress(email);
    }

    public List<GmailMailbox> findAll() {
        return repository.findAll();
    }
}
