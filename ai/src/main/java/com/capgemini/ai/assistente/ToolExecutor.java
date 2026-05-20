package com.capgemini.ai.assistente;

import java.util.Map;

public class ToolExecutor {
    public String execute(String toolName, Map<String, Object> args) {
        try {
            return switch (toolName) {
                case "tool1" -> "Resultado da ferramenta 1";
                case "tool2" -> "Resultado da ferramenta 2";
                default -> throw new IllegalArgumentException("Ferramenta não suportada: " + toolName);
            };
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar ferramenta: " + e.getMessage(), e);
        }
    }
}
