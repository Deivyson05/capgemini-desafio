package com.capgemini.ai.pedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, UUID> {
    public Optional<List<PedidoModel>> findByUsuarioEmail(String email);
}
