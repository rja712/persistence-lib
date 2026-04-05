package com.inboxintelligence.persistence.storage;

import com.inboxintelligence.persistence.config.EmailStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Slf4j
@RequiredArgsConstructor
public class LocalEmailStorageProvider implements EmailStorageProvider {

    private final EmailStorageProperties emailStorageProperties;

    private Path buildStoragePath(Long mailboxId, String messageId) {
        return Path.of(emailStorageProperties.localBasePath()).toAbsolutePath()
                .resolve(String.valueOf(mailboxId))
                .resolve(messageId);
    }

    @Override
    public String readContent(String storagePath) {

        if (storagePath == null || storagePath.isBlank()) {
            return "";
        }

        Path path = Path.of(storagePath);

        if (!Files.exists(path)) {
            log.warn("Storage file not found: {}", storagePath);
            return "";
        }

        try {
            return Files.readString(path);
        } catch (IOException e) {
            log.error("Failed to read storage file: {}", storagePath, e);
            throw new RuntimeException("Failed to read storage file: " + storagePath, e);
        }
    }

    @Override
    public String writeContent(Long mailboxId, String messageId, String fileName, String content) {

        if (content == null) {
            return null;
        }

        Path dir = buildStoragePath(mailboxId, messageId);
        try {
            Files.createDirectories(dir);

            Path filePath = dir.resolve(fileName);
            Files.writeString(filePath, content, StandardCharsets.UTF_8);

            log.debug("Content stored locally: {}", filePath);
            return filePath.toAbsolutePath().toString();

        } catch (IOException e) {
            log.error("Failed to write content to {}/{}", dir, fileName, e);
            throw new RuntimeException("Failed to write content", e);
        }
    }

    @Override
    public String writeBytes(Long mailboxId, String messageId, String folder, String fileName, byte[] data) {

        Path dir = buildStoragePath(mailboxId, messageId).resolve(folder);

        try {

            Files.createDirectories(dir);

            String safeFileName = Path.of(fileName).getFileName().toString();
            Path filePath = dir.resolve(safeFileName);

            if (Files.exists(filePath)) {
                log.debug("Filename '{}' already exists, deduplicating", safeFileName);
                String name = safeFileName;
                String ext = "";
                int dotIdx = safeFileName.lastIndexOf('.');

                if (dotIdx > 0) {
                    name = safeFileName.substring(0, dotIdx);
                    ext = safeFileName.substring(dotIdx);
                }

                int counter = 1;
                while (Files.exists(filePath)) {
                    filePath = dir.resolve(name + "_" + counter + ext);
                    counter++;
                }
            }

            Files.write(filePath, data);
            log.debug("Binary content stored locally: {} ({} bytes)", filePath.getFileName(), data.length);
            return filePath.toAbsolutePath().toString();

        } catch (IOException e) {
            log.error("Failed to write bytes to {}/{}", dir, fileName, e);
            throw new RuntimeException("Failed to write bytes", e);
        }
    }
}
