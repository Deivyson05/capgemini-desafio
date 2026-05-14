package com.capgemini.ai.categoria;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, UUID> {
    Optional<CategoriaModel> findByNome(String nome);
}
