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

    @Override
    public String readContent(String relativePath) {

        if (relativePath == null || relativePath.isBlank()) {
            return "";
        }

        Path absolutePath = resolveAbsolute(relativePath);

        if (!Files.exists(absolutePath)) {
            log.warn("Storage file not found: {} (resolved to {})", relativePath, absolutePath);
            return "";
        }

        try {
            return Files.readString(absolutePath);
        } catch (IOException e) {
            log.error("Failed to read storage file: {}", absolutePath, e);
            throw new RuntimeException("Failed to read storage file: " + relativePath, e);
        }
    }

    @Override
    public String writeContent(Long mailboxId, String messageId, String fileName, String content) {

        if (content == null) {
            return null;
        }

        Path relativeFilePath = buildRelativePath(mailboxId, messageId).resolve(fileName);
        Path absoluteFilePath = resolveAbsolute(relativeFilePath);

        try {
            Files.createDirectories(absoluteFilePath.getParent());
            Files.writeString(absoluteFilePath, content, StandardCharsets.UTF_8);
            log.debug("Content stored locally: {}", absoluteFilePath);
            return relativeFilePath.toString();
        } catch (IOException e) {
            log.error("Failed to write content to {}", absoluteFilePath, e);
            throw new RuntimeException("Failed to write content", e);
        }
    }

    @Override
    public String writeBytes(Long mailboxId, String messageId, String folder, String fileName, byte[] data) {

        Path relativeDir = buildRelativePath(mailboxId, messageId).resolve(folder);
        Path absoluteDir = resolveAbsolute(relativeDir);

        try {
            Files.createDirectories(absoluteDir);

            String safeFileName = Path.of(fileName).getFileName().toString();
            Path absoluteFilePath = deduplicate(absoluteDir.resolve(safeFileName));
            Files.write(absoluteFilePath, data);

            log.debug("Binary content stored locally: {} ({} bytes)", absoluteFilePath.getFileName(), data.length);
            return relativeDir.resolve(absoluteFilePath.getFileName()).toString();

        } catch (IOException e) {
            log.error("Failed to write bytes to {}/{}", absoluteDir, fileName, e);
            throw new RuntimeException("Failed to write bytes", e);
        }
    }

    private Path buildRelativePath(Long mailboxId, String messageId) {
        return Path.of(String.valueOf(mailboxId), messageId);
    }

    private Path resolveAbsolute(Path relativePath) {
        return Path.of(emailStorageProperties.localBasePath()).toAbsolutePath().resolve(relativePath);
    }

    private Path resolveAbsolute(String relativePath) {
        return resolveAbsolute(Path.of(relativePath));
    }

    private Path deduplicate(Path filePath) {
        if (!Files.exists(filePath)) {
            return filePath;
        }

        log.debug("Filename '{}' already exists, deduplicating", filePath.getFileName());
        String fileName = filePath.getFileName().toString();
        String name = fileName;
        String ext = "";
        int dotIdx = fileName.lastIndexOf('.');
        if (dotIdx > 0) {
            name = fileName.substring(0, dotIdx);
            ext = fileName.substring(dotIdx);
        }

        Path parent = filePath.getParent();
        int counter = 1;
        Path candidate;
        do {
            candidate = parent.resolve(name + "_" + counter + ext);
            counter++;
        } while (Files.exists(candidate));

        return candidate;
    }
}
