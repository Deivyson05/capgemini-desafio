package com.capgemini.ai.produto;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoModel create(ProdutoModel produtoModel) {
        return this.produtoRepository.save(produtoModel);
    }

    public ProdutoModel findById(String id) {
        return this.produtoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deleteById(String id) {
        this.produtoRepository.deleteById(UUID.fromString(id));
    }

    public ProdutoModel updateById(String id, ProdutoModel produtoModel) {
        var produto = this.produtoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setNome(produtoModel.getNome());
        produto.setDescricao(produtoModel.getDescricao());
        produto.setPreco(produtoModel.getPreco());
        produto.setImagemUrl(produtoModel.getImagemUrl());
        produto.setCategoria(produtoModel.getCategoria());
        return this.produtoRepository.save(produto);
    }

    public java.util.List<ProdutoModel> listAll() {
        return this.produtoRepository.findAll();
    }
}
