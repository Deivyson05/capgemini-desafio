package com.capgemini.ai.produto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping("/")
    public java.util.List<ProdutoModel> listAll() {
        var produtos = this.produtoService.listAll();
        return produtos;
    }

    @GetMapping("/{id}")
    public ProdutoModel findById(String id) {
        var produto = this.produtoService.findById(id);
        return produto;
    }

    @PostMapping("/")
    public ProdutoModel create(ProdutoModel produtoModel) {
        var produto = this.produtoService.create(produtoModel);
        return produto;
    }

    @PutMapping("/{id}")
    public ProdutoModel updateById(String id, ProdutoModel produtoModel) {
        var produto = this.produtoService.updateById(id, produtoModel);
        return produto;
    }

    @DeleteMapping("/{id}")
    public void deleteById(String id) {
        this.produtoService.deleteById(id);
    }
}
