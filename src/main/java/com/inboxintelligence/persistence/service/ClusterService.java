package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.Cluster;
import com.inboxintelligence.persistence.repository.ClusterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClusterService {

    private final ClusterRepository repository;

    public Cluster save(Cluster cluster) {
        return repository.save(cluster);
    }

    public Optional<Cluster> findById(Long id) {
        return repository.findById(id);
    }

    public List<Cluster> findByMailboxId(Long mailboxId) {
        return repository.findByGmailMailboxId(mailboxId);
    }

    public void deleteByMailboxId(Long mailboxId) {
        repository.deleteByGmailMailboxId(mailboxId);
    }
}
