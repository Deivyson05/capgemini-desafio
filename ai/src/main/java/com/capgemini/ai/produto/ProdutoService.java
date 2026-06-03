package com.capgemini.ai.produto;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.capgemini.ai.categoria.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoModel create(ProdutoModel produtoModel) {
        if (produtoModel.getCategoria() != null && produtoModel.getCategoria().getId() != null) {
            var categoria = categoriaRepository.findById(produtoModel.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produtoModel.setCategoria(categoria);
        }
        return produtoRepository.save(produtoModel);
    }

    public Optional<ProdutoModel> findById(UUID id) {
        return this.produtoRepository.findById(id);
    }

    public void deleteById(UUID id) {
        this.produtoRepository.deleteById(id);
    }

    public ProdutoModel updateById(UUID id, ProdutoModel produtoModel) {
        var produto = this.produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
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
