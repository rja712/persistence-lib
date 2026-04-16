package com.inboxintelligence.persistence.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email-storage-provider")
public record EmailStorageProperties(
        String name,
        String localBasePath
) {
}
