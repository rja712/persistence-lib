package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {

    List<Cluster> findByGmailMailboxId(Long mailboxId);

    @Modifying
    @Query("DELETE FROM Cluster c WHERE c.gmailMailboxId = :mailboxId")
    void deleteByGmailMailboxId(@Param("mailboxId") Long mailboxId);
}
