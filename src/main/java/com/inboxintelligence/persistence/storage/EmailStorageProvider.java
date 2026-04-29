package com.inboxintelligence.persistence.storage;

public interface EmailStorageProvider {

    String readContent(String storagePath);

    String writeContent(String email, String messageId, String fileName, String content);

    String writeBytes(String email, String messageId, String folder, String fileName, byte[] data);

    void deleteContent(String storagePath);

}
