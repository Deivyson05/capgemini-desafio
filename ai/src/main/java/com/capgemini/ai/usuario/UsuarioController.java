package com.capgemini.ai.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioModel>> listAll() {
        List<UsuarioModel> usuario = usuarioService.listAll();
        return ResponseEntity.ok().body(usuario);
    }
}
