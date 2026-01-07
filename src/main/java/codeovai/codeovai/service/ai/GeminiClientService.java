package codeovai.codeovai.service.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiClientService {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key=";

    public GeminiClientService() {
        this.restTemplate = new RestTemplate();
    }

    public String generateExplanation(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = buildRequestBody(prompt);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                GEMINI_ENDPOINT + apiKey,
                HttpMethod.POST,
                request,
                Map.class
        );

        return extractText(response.getBody());
    }

    private Map<String, Object> buildRequestBody(String prompt) {
        return Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                ),
                "generationConfig", Map.of(
                        "temperature", 1.0,
                        "topP", 0.8,
                        "maxOutputTokens", 1024
                )
        );
    }

    private String extractText(Map response) {
        try {
            var candidates = (List<Map>) response.get("candidates");
            var content = (Map) candidates.get(0).get("content");
            var parts = (List<Map>) content.get("parts");

            return parts.get(0).get("text").toString();
        } catch (Exception e) {
            return "ERROR: Unable to parse Gemini response.";
        }
    }
}
