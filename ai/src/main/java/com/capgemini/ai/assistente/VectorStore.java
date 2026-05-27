package com.capgemini.ai.assistente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class VectorStore {
    private final List<String> chunks = new ArrayList<>();
    private boolean indexed = false;
    public void index(List<String> newChunks) {
        if (indexed) return;
        chunks.addAll(newChunks);
        indexed = true;
    }
    public String retrieve(String query, int k) {
        String[] words = query.toLowerCase().split("\\s+");
        return chunks.stream()
            .filter(c -> Arrays.stream(words).anyMatch(w -> c.toLowerCase().contains(w)))
            .limit(k)
            .collect(Collectors.joining("\n\n---\n\n"));
    }
}
