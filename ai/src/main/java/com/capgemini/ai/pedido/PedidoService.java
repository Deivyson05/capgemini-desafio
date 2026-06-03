package com.capgemini.ai.pedido;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.capgemini.ai.produto.ProdutoModel;
import com.capgemini.ai.produto.ProdutoService;
import com.capgemini.ai.usuario.UsuarioModel;
import com.capgemini.ai.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoService produtoService;

    public PedidoModel create(PedidoModel request) {
        UsuarioModel usuario = usuarioRepository.findById(request.getUsuario().getId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<ProdutoModel> produtos = request.getProdutos().stream()
            .map(p -> produtoService.findById(p.getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado")))
            .toList();

        PedidoModel pedido = PedidoModel.builder()
            .usuario(usuario)
            .produtos(produtos)
            .createdAt(LocalDate.now())
            .previstoEntrega(LocalDate.now().plusDays(10))
            .status("Preparando")
            .build();

        // Garantir datas
        LocalDate createdAt = LocalDate.now();
        pedido.setCreatedAt(createdAt);
        pedido.setPrevistoEntrega(createdAt.plusDays(10));

        // Status inicial
        pedido.setStatus("Preparando");

        return pedidoRepository.save(pedido);
    }

    public List<PedidoModel> listAll() {
        return this.pedidoRepository.findAll();
    }

    public void deleteById(UUID id) {
        this.pedidoRepository.deleteById(id);
    }

    public PedidoModel updateById(UUID id, PedidoModel pedidoModel) {
        var pedido = this.pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setUsuario(pedidoModel.getUsuario());
        pedido.setProdutos(pedidoModel.getProdutos());
        return this.pedidoRepository.save(pedido);
    }

    public PedidoModel findById(UUID id) {
        return this.pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<PedidoModel> findByUsuarioId(UUID usuarioId) {
        return this.pedidoRepository.findAll().stream().filter(p -> p.getUsuario().getId().equals(usuarioId)).toList();
    }

    public List<PedidoModel> findByUsuarioEmail(String email) {
        return this.pedidoRepository.findByUsuarioEmail(email).orElseThrow(() -> new RuntimeException("Usuário nao encontrado"));
    }

    //definir status do pedido como cancelado
    public PedidoModel cancelPedido(UUID id) {
        var pedido = this.pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus("Cancelado");
        return this.pedidoRepository.save(pedido);
    }
}
