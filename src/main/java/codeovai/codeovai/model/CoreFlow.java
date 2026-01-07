package codeovai.codeovai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CoreFlow {

    private String name;
    private List<String> steps;
    private String entryPoint;

}
