package codeovai.codeovai.service.upload;

import codeovai.codeovai.model.SourceFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FileScannerService {

    private static final Set<String> IGNORED_DIRECTORIES = Set.of(
            "target", "build", "node_modules", ".git", ".idea", "dist", "out"
    );

    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of(
            ".java", ".kt", ".js", ".ts", ".php"
    );

    /**
     * Scans a backend project and returns relevant source files.
     */
    public List<SourceFile> scanRelevantFiles(Path systemRoot) {
        List<SourceFile> result = new ArrayList<>();

        try {
            Files.walk(systemRoot)
                    .filter(Files::isRegularFile)
                    .filter(this::isSupportedSourceFile)
                    .filter(path -> !isIgnored(path))
                    .forEach(path -> result.add(classify(path, systemRoot)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan project files", e);
        }

        return result;
    }

    private boolean isSupportedSourceFile(Path path) {
        String fileName = path.getFileName().toString().toLowerCase();
        return SUPPORTED_EXTENSIONS.stream().anyMatch(fileName::endsWith);
    }

    private boolean isIgnored(Path path) {
        for (Path part : path) {
            if (IGNORED_DIRECTORIES.contains(part.toString())) {
                return true;
            }
        }
        return false;
    }

    private SourceFile classify(Path filePath, Path root) {
        String relativePath = root.relativize(filePath).toString();
        String type = detectType(relativePath.toLowerCase());

        return new SourceFile(relativePath, type);
    }

    private String detectType(String path) {
        if (path.contains("controller")) return "CONTROLLER";
        if (path.contains("service")) return "SERVICE";
        if (path.contains("repository") || path.contains("dao")) return "REPOSITORY";
        if (path.contains("model") || path.contains("entity")) return "MODEL";
        if (path.contains("config")) return "CONFIG";
        return "OTHER";
    }
}
