package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.ClusterAssignmentType;
import com.inboxintelligence.persistence.model.entity.EmailEnrichment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EmailEnrichmentRepository extends JpaRepository<EmailEnrichment, Long> {

    Optional<EmailEnrichment> findByEmailContentId(Long emailContentId);

    @Query("""
            SELECT ee.id, ee.embedding
            FROM EmailEnrichment ee
            JOIN EmailContent ec ON ee.emailContentId = ec.id
            WHERE ec.gmailMailboxId = :mailboxId
              AND ee.embedding IS NOT NULL
            """)
    List<Object[]> findIdAndEmbeddingByGmailMailboxId(@Param("mailboxId") Long mailboxId);

    @Modifying
    @Query("""
            UPDATE EmailEnrichment ee
            SET ee.clusterId            = :clusterId,
                ee.clusterAssignmentType = :type,
                ee.updatedAt            = :now
            WHERE ee.id IN :ids
            """)
    void bulkAssignCluster(
            @Param("ids")      List<Long> ids,
            @Param("clusterId") Long clusterId,
            @Param("type")     ClusterAssignmentType type,
            @Param("now")      Instant now);
}
