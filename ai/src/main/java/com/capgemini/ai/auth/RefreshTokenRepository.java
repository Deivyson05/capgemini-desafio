package com.capgemini.ai.auth;

import java.util.Optional;

import com.capgemini.ai.usuario.UsuarioModel;

public interface RefreshTokenRepository {
    Optional<RefreshTokenModel> findByToken(String token);
    void deleteByUsuario(UsuarioModel usuario);
    RefreshTokenModel save(RefreshTokenModel refreshToken);
}
