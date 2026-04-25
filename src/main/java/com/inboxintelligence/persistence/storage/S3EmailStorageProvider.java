package com.inboxintelligence.persistence.storage;

public class S3EmailStorageProvider implements EmailStorageProvider {

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
