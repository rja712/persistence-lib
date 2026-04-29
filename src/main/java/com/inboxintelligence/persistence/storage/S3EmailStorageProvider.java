package com.inboxintelligence.persistence.storage;

public class S3EmailStorageProvider implements EmailStorageProvider {

    @Override
    public String readContent(String storagePath) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }

    @Override
    public String writeContent(String email, String messageId, String fileName, String content) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }

    @Override
    public String writeBytes(String email, String messageId, String folder, String fileName, byte[] data) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }

    @Override
    public void deleteContent(String storagePath) {
        throw new UnsupportedOperationException("S3EmailStorageProvider is not implemented");
    }
}
