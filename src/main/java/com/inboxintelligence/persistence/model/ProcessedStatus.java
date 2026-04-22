package com.inboxintelligence.persistence.model;

public enum ProcessedStatus {

    //Used by Ingester Application
    RECEIVED,
    CONTENT_SAVED,
    ATTACHMENT_SAVED,
    INGESTION_FAILED,
    PUBLISHED_FOR_SANITIZATION,

    //Used by Processor Application
    SANITIZATION_STARTED,
    SANITIZATION_COMPLETED,
    PUBLISHED_FOR_EMBEDDING,
    EMBEDDING_STARTED,
    EMBEDDING_GENERATED,
    CLUSTER_ASSIGNMENT_STARTED,
    CLUSTER_ASSIGNED,
    PROCESSING_FAILED
}
