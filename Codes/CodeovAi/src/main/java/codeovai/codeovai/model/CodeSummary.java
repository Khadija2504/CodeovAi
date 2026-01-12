package codeovai.codeovai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CodeSummary {

    private List<CodeElement> controllers;
    private List<CodeElement> services;
    private List<CodeElement> models;
    private List<CodeElement> endpoints;

}
