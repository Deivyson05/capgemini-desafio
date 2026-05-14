package com.capgemini.ai.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    Optional<UsuarioModel> findByEmail(String email);
    Optional<UsuarioModel> findById(Long id);
    boolean existsByEmail(String email);
    List<UsuarioModel> findAll();
}
