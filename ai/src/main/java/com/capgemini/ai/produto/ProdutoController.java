package com.capgemini.ai.produto;

import java.util.UUID;

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
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping("")
    public java.util.List<ProdutoModel> listAll() {
        var produtos = this.produtoService.listAll();
        return produtos;
    }

    @GetMapping("/{id}")
    public ProdutoModel findById(@PathVariable UUID id) {
        var produto = this.produtoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado"));
        return produto;
    }

    @PostMapping("")
    public ProdutoModel create(@RequestBody ProdutoModel produtoModel) {
        var produto = this.produtoService.create(produtoModel);
        return produto;
    }

    @PutMapping("/{id}")
    public ProdutoModel updateById(@PathVariable UUID id, @RequestBody ProdutoModel produtoModel) {
        var produto = this.produtoService.updateById(id, produtoModel);
        return produto;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        this.produtoService.deleteById(id);
    }
}
