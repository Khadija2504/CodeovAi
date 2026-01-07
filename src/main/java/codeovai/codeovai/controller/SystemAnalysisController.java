package codeovai.codeovai.controller;

import codeovai.codeovai.model.CodeSummary;
import codeovai.codeovai.model.SourceFile;
import codeovai.codeovai.model.SystemContext;
import codeovai.codeovai.service.analyze.CodeSummarizationService;
import codeovai.codeovai.service.analyze.SystemContextBuilderService;
import codeovai.codeovai.service.upload.FileScannerService;
import codeovai.codeovai.service.upload.SystemUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/codeovAi")
public class SystemAnalysisController {

    @Autowired
    private SystemUploadService systemUploadService;

    @Autowired
    private FileScannerService fileScannerService;

    @Autowired
    private CodeSummarizationService codeSummarizationService;

    @Autowired
    private SystemContextBuilderService systemContextBuilderService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return ResponseEntity
                    .badRequest()
                    .body("No files uploaded.");
        }

        System.out.println("files uploaded into the controller");
        System.out.println(files.length);

        try {
            List<SourceFile> results = new ArrayList<>();

            System.out.println("empty source files" + results);

            CodeSummary codeSummary = null;
            for (MultipartFile file : files) {
                String systemId = systemUploadService.uploadSystem(file);
                System.out.println("systemId" + systemId);
                Path path = systemUploadService.getSystemRoot(systemId);

                System.out.println("path" + path);
                List<SourceFile> scannedFiles = fileScannerService.scanRelevantFiles(path);
                System.out.println("scannedFiles" + scannedFiles);
                results.addAll(scannedFiles);
                codeSummary = codeSummarizationService.summarize(results, path);
                System.out.println("codeSummary" + codeSummary);
            }
            SystemContext systemContext = systemContextBuilderService.buildContext(codeSummary);
            System.out.println(systemContext);

            if (results.size() == 1) {
                return ResponseEntity.ok(results.get(0));
            }
            else {

                return ResponseEntity.ok(systemContext);
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing files: " + e.getMessage());
        }
    }
}