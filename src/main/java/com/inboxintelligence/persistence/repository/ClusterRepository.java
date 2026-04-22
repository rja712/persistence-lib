package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {

    List<Cluster> findByGmailMailboxId(Long mailboxId);
}
