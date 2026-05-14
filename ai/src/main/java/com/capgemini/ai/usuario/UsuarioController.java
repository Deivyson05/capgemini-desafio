package com.capgemini.ai.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    @GetMapping("/")
    public ResponseEntity<List<UsuarioModel>> listAll() {
        List<UsuarioModel> usuario = usuarioService.listAll();
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/")
    public ResponseEntity<UsuarioModel> create(@RequestBody UsuarioModel usuarioModel) {
        UsuarioModel usuario = this.usuarioService.create(usuarioModel);
        return ResponseEntity.ok().body(usuario);
    }
}
