package com.inboxintelligence.persistence.storage;

import org.springframework.stereotype.Component;

@Component
public class S3EmailStorageProvider implements EmailStorageProvider {

    private String buildStoragePath(Long mailboxId, String messageId) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }

    @Override
    public String readContent(String storagePath) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }

    @Override
    public String writeContent(Long mailboxId, String messageId, String fileName, String content) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }

    @Override
    public String writeBytes(Long mailboxId, String messageId, String folder, String fileName, byte[] data) {
         throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }
}
