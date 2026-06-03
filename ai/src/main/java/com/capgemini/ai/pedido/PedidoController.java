package com.capgemini.ai.pedido;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping("")
    public ResponseEntity<List<PedidoModel>> listAll() {
        var pedidos = this.pedidoService.listAll();
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoModel>> findByUsuarioId(@PathVariable UUID usuarioId) {
        var pedidos = this.pedidoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok().body(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> findById(@PathVariable UUID id) {
        var pedido = this.pedidoService.findById(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping("")
    public ResponseEntity<PedidoModel> create(@RequestBody PedidoModel pedidoModel) {
        var pedido = this.pedidoService.create(pedidoModel);
        return ResponseEntity.ok().body(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoModel> updateById(UUID id, @RequestBody PedidoModel pedidoModel) {
        var pedido = this.pedidoService.updateById(id, pedidoModel);
        return ResponseEntity.ok().body(pedido);
    }

    @DeleteMapping("/{id}")
    public void deleteById(UUID id) {
        this.pedidoService.deleteById(id);
    }

}
