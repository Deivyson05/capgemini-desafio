package com.capgemini.ai.auth;

import org.springframework.stereotype.Service;

import com.capgemini.ai.usuario.UsuarioRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    public String login(String email, String password) {
        var usuario = this.usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        var result = BCrypt.verifyer().verify(password.toCharArray(), usuario.getSenha());
        if (!result.verified) {
            throw new RuntimeException("Senha incorreta");
        }
        return usuario.getId().toString();
    }
}
