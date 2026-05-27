package com.capgemini.ai.assistente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class SessionHistoryManager {
    private final Map<String, List<Map<String, String>>> histories = new ConcurrentHashMap<>();
    private final Map<String, String> contexts = new ConcurrentHashMap<>();

    public List<Map<String, String>> getHistory(String sessionId) {
        return histories.computeIfAbsent(sessionId, k -> new ArrayList<>());
    }
    public void append(String sessionId, String role, String content) {
        histories.computeIfAbsent(sessionId, k -> new ArrayList<>())
                 .add(Map.of("role", role, "content", content));
    }

    public void saveContext(String sessionId, String context) {
        contexts.put(sessionId, context);
    }

    public String getContext(String sessionId) {
        return contexts.getOrDefault(sessionId, "");
    }

}
