package com.capgemini.ai.auth;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.capgemini.ai.usuario.UsuarioModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenModel criar(UsuarioModel usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);

        RefreshTokenModel refreshToken = RefreshTokenModel.builder()
                .usuario(usuario)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenModel validar(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
    }
}
