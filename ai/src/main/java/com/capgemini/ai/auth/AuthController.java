package com.capgemini.ai.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.ai.usuario.UsuarioModel;
import com.capgemini.ai.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginModel request) {
        UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // valide a senha aqui com BCrypt...

        String accessToken = jwtService.gerarToken(usuario);
        RefreshTokenModel refreshToken = refreshTokenService.criar(usuario);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String token = body.get("refreshToken");

        RefreshTokenModel refreshToken = refreshTokenService.validar(token);
        String novoAccessToken = jwtService.gerarToken(refreshToken.getUsuario());

        return ResponseEntity.ok(Map.of(
                "accessToken", novoAccessToken
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        // invalida o refresh token no banco
        UsuarioModel usuario = usuarioRepository.findByEmail(body.get("email"))
                .orElseThrow();
        refreshTokenRepository.deleteByUsuario(usuario);
        return ResponseEntity.ok("Logout realizado");
    }
}
