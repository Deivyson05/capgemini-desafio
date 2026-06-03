package com.capgemini.ai.assistente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GroqClient {
    private static final String MODEL = "llama-3.3-70b-versatile";
    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${groq.api-key:${GROQ_API_KEY:}}")
    private String apiKey;

    public String complete(String systemPrompt, List<Map<String, String>> history) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("A propriedade 'groq.api-key' não está configurada.");
        }

        try {
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", systemPrompt));
            messages.addAll(history);

            Map<String, Object> body = Map.of(
                "model", MODEL,
                "messages", messages
            );

            HttpClient http = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(GROQ_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .build();

            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(res.body());
            return root.at("/choices/0/message/content").asText();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao chamar Groq: " + e.getMessage(), e);
        }
    }
}
