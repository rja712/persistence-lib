package com.inboxintelligence.persistence.service;

import com.inboxintelligence.persistence.model.LabelSource;
import com.inboxintelligence.persistence.model.entity.Label;
import com.inboxintelligence.persistence.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    @Transactional(readOnly = true)
    public List<Label> findByMailboxId(Long gmailMailboxId) {
        return labelRepository.findByGmailMailboxId(gmailMailboxId);
    }

    @Transactional(readOnly = true)
    public List<Label> findActiveByMailboxId(Long gmailMailboxId) {
        return labelRepository.findByGmailMailboxIdAndIsActiveTrue(gmailMailboxId);
    }

    @Transactional(readOnly = true)
    public List<Label> findByMailboxIdAndSource(Long gmailMailboxId, LabelSource source) {
        return labelRepository.findByGmailMailboxIdAndSource(gmailMailboxId, source);
    }

    @Transactional(readOnly = true)
    public Optional<Label> findByMailboxIdAndFullName(Long gmailMailboxId, String fullName) {
        return labelRepository.findByGmailMailboxIdAndFullName(gmailMailboxId, fullName);
    }

    @Transactional
    public Label save(Label label) {
        return labelRepository.save(label);
    }

    @Transactional
    public List<Label> saveAll(List<Label> labels) {
        return labelRepository.saveAll(labels);
    }

    /**
     * Upsert a USER-sourced label discovered during Gmail sync.
     * If a label with the same fullName exists (any source, active or inactive),
     * reactivates it; otherwise inserts a fresh USER label.
     * Returns true if this call materially changed state (insert or reactivation).
     */
    @Transactional
    public boolean upsertUserLabel(Long gmailMailboxId, String displayName, String fullName) {

        Optional<Label> existing = labelRepository.findByGmailMailboxIdAndFullName(gmailMailboxId, fullName);

        if (existing.isPresent()) {
            Label label = existing.get();
            if (Boolean.TRUE.equals(label.getIsActive())) {
                return false;
            }
            label.setIsActive(true);
            labelRepository.save(label);
            return true;
        }

        labelRepository.save(Label.builder()
                .gmailMailboxId(gmailMailboxId)
                .displayName(displayName)
                .fullName(fullName)
                .source(LabelSource.USER)
                .isActive(true)
                .build());
        return true;
    }

    /**
     * Soft-delete a USER label that no longer exists on the Gmail side.
     * SYSTEM-sourced labels are owned by the LLM and must never be deactivated by Gmail sync.
     */
    @Transactional
    public void deactivateUserLabel(Label label) {

        if (label.getSource() != LabelSource.USER) {
            return;
        }
        if (Boolean.FALSE.equals(label.getIsActive())) {
            return;
        }
        label.setIsActive(false);
        labelRepository.save(label);
    }
}
