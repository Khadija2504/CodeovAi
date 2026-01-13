package codeovai.codeovai.service.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class SystemUploadService {

    private static final String BASE_UPLOAD_DIR = "uploads";

    /**
     * Uploads and extracts a backend system ZIP.
     *
     * @param zipFile uploaded project
     * @return generated systemId
     */
    public String uploadSystem(MultipartFile zipFile) {
        validateZip(zipFile);

        String systemId = UUID.randomUUID().toString();
        Path systemRoot = Paths.get(BASE_UPLOAD_DIR, systemId);

        try {
            Files.createDirectories(systemRoot);
            extractZip(zipFile.getInputStream(), systemRoot);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload system", e);
        }

        return systemId;
    }

    /**
     * Returns the root path of an uploaded system.
     */
    public Path getSystemRoot(String systemId) {
        return Paths.get(BASE_UPLOAD_DIR, systemId);
    }

    private void validateZip(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        if (!file.getOriginalFilename().endsWith(".zip")) {
            throw new IllegalArgumentException("Only ZIP files are supported");
        }
    }

    private void extractZip(InputStream inputStream, Path targetDir) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path resolvedPath = targetDir.resolve(entry.getName()).normalize();

                if (!resolvedPath.startsWith(targetDir)) {
                    throw new IOException("Bad ZIP entry");
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(resolvedPath);
                } else {
                    Files.createDirectories(resolvedPath.getParent());
                    Files.copy(zipInputStream, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
