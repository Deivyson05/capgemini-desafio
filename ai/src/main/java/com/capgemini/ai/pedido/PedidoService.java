package com.capgemini.ai.pedido;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoModel create(PedidoModel pedidoModel) {
        return this.pedidoRepository.save(pedidoModel);
    }

    public List<PedidoModel> listAll() {
        return this.pedidoRepository.findAll();
    }

    public void deleteById(String id) {
        this.pedidoRepository.deleteById(UUID.fromString(id));
    }

    public PedidoModel updateById(String id, PedidoModel pedidoModel) {
        var pedido = this.pedidoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setUsuario(pedidoModel.getUsuario());
        pedido.setProdutos(pedidoModel.getProdutos());
        return this.pedidoRepository.save(pedido);
    }

    public PedidoModel findById(String id) {
        return this.pedidoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<PedidoModel> findByUsuarioId(String usuarioId) {
        return this.pedidoRepository.findAll().stream().filter(p -> p.getUsuario().getId().toString().equals(usuarioId)).toList();
    }
}
