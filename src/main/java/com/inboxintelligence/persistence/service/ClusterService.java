package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.Cluster;
import com.inboxintelligence.persistence.repository.ClusterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClusterService {

    private final ClusterRepository repository;

    @Transactional
    public Cluster save(Cluster cluster) {
        return repository.save(cluster);
    }

    @Transactional
    public List<Cluster> saveAll(List<Cluster> clusters) {
        return repository.saveAll(clusters);
    }

    @Transactional(readOnly = true)
    public Optional<Cluster> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cluster> findByMailboxId(Long mailboxId) {
        return repository.findByGmailMailboxId(mailboxId);
    }

    @Transactional
    public void deleteByMailboxId(Long mailboxId) {
        repository.deleteByGmailMailboxId(mailboxId);
    }
}
