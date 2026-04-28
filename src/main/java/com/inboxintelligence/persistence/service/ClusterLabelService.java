package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.entity.ClusterLabel;
import com.inboxintelligence.persistence.repository.ClusterLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClusterLabelService {

    private final ClusterLabelRepository clusterLabelRepository;

    @Transactional(readOnly = true)
    public Optional<ClusterLabel> findByClusterId(Long clusterId) {
        return clusterLabelRepository.findByClusterId(clusterId);
    }

    @Transactional(readOnly = true)
    public List<ClusterLabel> findByLabelId(Long labelId) {
        return clusterLabelRepository.findByLabelId(labelId);
    }

    @Transactional
    public ClusterLabel save(ClusterLabel clusterLabel) {
        return clusterLabelRepository.save(clusterLabel);
    }

    @Transactional
    public List<ClusterLabel> saveAll(List<ClusterLabel> clusterLabels) {
        return clusterLabelRepository.saveAll(clusterLabels);
    }

    @Transactional
    public void deleteByClusterId(Long clusterId) {
        clusterLabelRepository.deleteByClusterId(clusterId);
    }

    @Transactional
    public void deleteByClusterIds(List<Long> clusterIds) {
        clusterLabelRepository.deleteByClusterIdIn(clusterIds);
    }
}
