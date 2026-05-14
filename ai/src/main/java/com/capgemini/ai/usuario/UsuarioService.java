package com.capgemini.ai.usuario;

import java.util.List;

import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<UsuarioModel> listAll() {
        return usuarioRepository.findAll();
    }

    public UsuarioModel create(UsuarioModel usuarioModel) {
        var user = this.usuarioRepository.findByEmail(usuarioModel.getEmail());
        if (user.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        
        var hash = BCrypt.withDefaults().hashToString(12, usuarioModel.getSenha().toCharArray());
        usuarioModel.setSenha(hash);
        return this.usuarioRepository.save(usuarioModel);
    }
}
