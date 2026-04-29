package com.inboxintelligence.persistence.storage;

import com.inboxintelligence.persistence.config.EmailStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.InvalidPathException;

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
    public String writeContent(String email, String messageId, String fileName, String content) {

        if (content == null) {
            return null;
        }

        Path relativeFilePath = buildRelativePath(email, messageId).resolve(fileName);
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
    public String writeBytes(String email, String messageId, String folder, String fileName, byte[] data) {

        Path relativeDir = buildRelativePath(email, messageId).resolve(folder);
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

    @Override
    public void deleteContent(String relativePath) {

        if (relativePath == null || relativePath.isBlank()) {
            return;
        }

        Path absolutePath = resolveAbsolute(relativePath);
        try {
            boolean deleted = Files.deleteIfExists(absolutePath);
            if (deleted) {
                log.debug("Deleted storage file: {}", absolutePath);
            }
        } catch (IOException e) {
            log.warn("Failed to delete storage file: {} — {}", absolutePath, e.getMessage());
        }
    }

    private Path buildRelativePath(String email, String messageId) {
        return Path.of(email, messageId);
    }

    private Path resolveAbsolute(Path relativePath) {
        Path base = Path.of(emailStorageProperties.localBasePath()).toAbsolutePath().normalize();
        Path resolved = base.resolve(relativePath).normalize();
        if (!resolved.startsWith(base)) {
            throw new IllegalArgumentException("Path traversal attempt blocked: " + relativePath);
        }
        return resolved;
    }

    private Path resolveAbsolute(String relativePath) {
        try {
            return resolveAbsolute(Path.of(relativePath));
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Invalid storage path: " + relativePath, e);
        }
    }

    private static final int DEDUPLICATE_MAX_ATTEMPTS = 1000;

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
        for (int counter = 1; counter <= DEDUPLICATE_MAX_ATTEMPTS; counter++) {
            Path candidate = parent.resolve(name + "_" + counter + ext);
            if (!Files.exists(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException("Could not deduplicate filename after " + DEDUPLICATE_MAX_ATTEMPTS + " attempts: " + filePath.getFileName());
    }
}
