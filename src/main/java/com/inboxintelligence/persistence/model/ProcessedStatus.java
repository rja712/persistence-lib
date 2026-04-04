package com.inboxintelligence.persistence.model;

public enum ProcessedStatus {
    RECEIVED,
    CONTENT_SAVED,
    ATTACHMENT_SAVED,
    SANITIZATION_STARTED,
    SANITIZATION_COMPLETED,
    EMBEDDING_GENERATED,
    EMBEDDING_SAVED,
    PROCESSING_FAILED
}
