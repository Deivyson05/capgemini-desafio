package com.capgemini.ai.auth;

import java.time.Instant;

import com.capgemini.ai.usuario.UsuarioModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsuarioModel usuario;

    @Column(nullable = false)
    private Instant expiryDate;
}
