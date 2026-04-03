package com.inboxintelligence.persistence.storage;

public interface EmailStorageProvider {

    String readContent(String storagePath);

    String writeContent(String directoryPath, String fileName, String content);

    String writeBytes(String directoryPath, String fileName, byte[] data);

}
