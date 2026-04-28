package com.inboxintelligence.persistence.repository;

import com.inboxintelligence.persistence.model.entity.ClusterLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClusterLabelRepository extends JpaRepository<ClusterLabel, Long> {

    Optional<ClusterLabel> findByClusterId(Long clusterId);

    List<ClusterLabel> findByLabelId(Long labelId);

    void deleteByClusterId(Long clusterId);

    void deleteByClusterIdIn(List<Long> clusterIds);
}
