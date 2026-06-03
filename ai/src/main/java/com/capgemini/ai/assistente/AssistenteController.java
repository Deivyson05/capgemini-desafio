package com.capgemini.ai.assistente;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/assistente")
@AllArgsConstructor
public class AssistenteController {
    private final AssistenteService assistenteService;

    @PostMapping("/mensagem")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody AssistenteRequestDTO dto) {
        String reply = assistenteService.sendMessage(dto.message(), dto.sessionId());
        return ResponseEntity.ok(Map.of("reply", reply));
    }
}
