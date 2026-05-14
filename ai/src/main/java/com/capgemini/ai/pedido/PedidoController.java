package com.capgemini.ai.pedido;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping("/")
    public List<PedidoModel> listAll() {
        var pedidos = this.pedidoService.listAll();
        return pedidos;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<PedidoModel> findByUsuarioId(String usuarioId) {
        var pedidos = this.pedidoService.findByUsuarioId(usuarioId);
        return pedidos;
    }

    @GetMapping("/{id}")
    public PedidoModel findById(String id) {
        var pedido = this.pedidoService.findById(id);
        return pedido;
    }

    @PostMapping("/")
    public PedidoModel create(PedidoModel pedidoModel) {
        var pedido = this.pedidoService.create(pedidoModel);
        return pedido;
    }

    @PutMapping("/{id}")
    public PedidoModel updateById(String id, PedidoModel pedidoModel) {
        var pedido = this.pedidoService.updateById(id, pedidoModel);
        return pedido;
    }

    @DeleteMapping("/{id}")
    public void deleteById(String id) {
        this.pedidoService.deleteById(id);
    }

}
