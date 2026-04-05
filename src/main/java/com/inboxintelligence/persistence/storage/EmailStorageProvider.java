package com.inboxintelligence.persistence.storage;

public interface EmailStorageProvider {

    String readContent(String storagePath);

    String writeContent(Long mailboxId, String messageId, String fileName, String content);

    String writeBytes(Long mailboxId, String messageId, String folder, String fileName, byte[] data);

}
