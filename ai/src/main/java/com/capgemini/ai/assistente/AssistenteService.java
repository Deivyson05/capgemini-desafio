package com.capgemini.ai.assistente;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssistenteService {
    private final GroqClient groqClient;
    private final PromptBuilder promptBuilder;
    private final ToolExecutor toolExecutor;
    private final SessionHistoryManager sessions;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final Pattern JSON_BLOCK = Pattern.compile("(\\{[^{}]*})", Pattern.DOTALL);

    public String sendMessage(String message, String sessionId) {

        String systemPrompt = promptBuilder.build();

        if (message != null && !message.isBlank()) {
            sessions.append(sessionId, "user", message);
        }

        List<Map<String, String>> history = sessions.getHistory(sessionId);

        String reply = groqClient.complete(systemPrompt, history);

        String replyLimpo = JSON_BLOCK.matcher(reply).replaceAll("").strip();
        sessions.append(sessionId, "assistant", replyLimpo.isBlank() ? "[comando executado]" : replyLimpo);

        Matcher matcher = JSON_BLOCK.matcher(reply);

        if (!matcher.find())
            return reply;

        try {
            String jsonStr = matcher.group(1).strip();
            Map<String, Object> dados = mapper.readValue(jsonStr, new TypeReference<>() {
            });
            String action = (String) dados.get("action");

            String resultado = toolExecutor.execute(action, dados);

            if ("agendar".equals(action)) {
                return "Agendamento realizado com sucesso!\n" + resultado;
            }

            sessions.append(sessionId, "user", "Resultado da ação: " + action + ": " + resultado);

            return sendMessage("", sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            String erro = "Resultado da ação com erro: " + e.getMessage();
            sessions.append(sessionId, "user", erro);
            return sendMessage("", sessionId);
        }
    }
}