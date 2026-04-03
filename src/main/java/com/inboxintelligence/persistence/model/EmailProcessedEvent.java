package com.inboxintelligence.persistence.model;

public record EmailProcessedEvent(
        Long emailContentId
) {
}
