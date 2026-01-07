package codeovai.codeovai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CodeElement {

    private String name;
    private String elementType;
    private String sourceType;
}
