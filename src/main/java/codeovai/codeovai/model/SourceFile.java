package codeovai.codeovai.model;

import lombok.Data;

@Data
public class SourceFile {

    private String path;
    private String type;

    public SourceFile(String path, String type) {
        this.path = path;
        this.type = type;
    }
}
