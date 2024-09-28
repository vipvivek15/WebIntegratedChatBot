package org.example.taxauditwebapp.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";

    public String generateResponse(String userPrompt) {
        // Setup the HTTP request to the GPT API
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{Map.of("role", "user", "content", userPrompt)});

        // Increase max_tokens to allow for longer, more complete responses
        requestBody.put("max_tokens", 300); // Adjust based on your needs and usage balance
        requestBody.put("temperature", 0.7); // Adjust creativity of the response if needed

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the request to the GPT API
            ResponseEntity<Map> response = restTemplate.exchange(GPT_API_URL, HttpMethod.POST, entity, Map.class);

            // Process the response from GPT
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                    String content = (String) message.get("content");

                    // Render Markdown to HTML using CommonMark
                    String renderedHtml = renderMarkdownToHtml(content);

                    // Return the HTML-rendered content
                    return renderedHtml.trim();
                }
            }
        } catch (Exception e) {
            // Handle exceptions such as connection errors or API-related issues
            return "Error: Could not get a valid response from GPT. Please try again.";
        }

        return "Error: Could not get a valid response from GPT.";
    }

    // Helper method to convert Markdown to HTML using CommonMark
    private String renderMarkdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
