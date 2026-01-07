package codeovai.codeovai.service.ai;

import codeovai.codeovai.model.CoreFlow;
import codeovai.codeovai.model.SystemContext;
import org.springframework.stereotype.Service;

@Service
public class GeminiPromptService {

    public String buildPrompt(SystemContext context) {
        StringBuilder prompt = new StringBuilder();

        prompt.append(systemInstruction());
        prompt.append(systemContextSection(context));
        prompt.append(taskInstruction());
        prompt.append(outputFormatInstruction());

        return prompt.toString();
    }

    private String systemInstruction() {
        return """
        You are CodeovAi, an AI engine designed to explain software systems.

        Core rules:
        - Do NOT hallucinate or invent details.
        - If information is missing, explicitly say "Unknown".
        - Base explanations ONLY on the provided context.
        - Be concise, technical, and precise.
        - Explain like a senior engineer onboarding a new developer.

        """;
    }

    private String systemContextSection(SystemContext context) {
        StringBuilder section = new StringBuilder();

        section.append("SYSTEM CONTEXT:\n");
        section.append("System Purpose (inferred): ")
                .append(context.getSystemPurpose()).append("\n\n");

        section.append("Architecture Overview:\n")
                .append(context.getArchitectureOverview()).append("\n\n");

        section.append("Core Flow:\n");
        section.append(formatCoreFlow(context.getCoreFlow())).append("\n");

        section.append("Assumptions:\n");
        context.getAssumptions()
                .forEach(a -> section.append("- ").append(a).append("\n"));

        section.append("\nUnknowns:\n");
        context.getUnknowns()
                .forEach(u -> section.append("- ").append(u).append("\n"));

        section.append("\n");
        return section.toString();
    }

    private String taskInstruction() {
        return """
        TASK:
        1. Explain the system in clear technical terms.
        2. Explain the core flow step by step.
        3. Identify risky or fragile areas.
        4. Suggest safe modification advice.
        5. Explicitly state uncertainties.

        """;
    }

    private String outputFormatInstruction() {
        return """
        OUTPUT FORMAT (STRICT):
        
        SYSTEM_PURPOSE:
        <text>

        ARCHITECTURE:
        <text>

        CORE_FLOW:
        - Step 1
        - Step 2
        - Step 3

        RISK_ZONES:
        - Location: <text>
          Reason: <text>
          Severity: LOW | MEDIUM | HIGH

        SAFE_CHANGE_ADVICE:
        - <text>
        - <text>

        UNCERTAINTIES:
        - <text>

        """;
    }

    private String formatCoreFlow(CoreFlow flow) {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(flow.getName()).append("\n");
        builder.append("Entry Point: ").append(flow.getEntryPoint()).append("\n");
        builder.append("Steps:\n");

        flow.getSteps()
                .forEach(step -> builder.append("- ").append(step).append("\n"));

        return builder.toString();
    }
}
