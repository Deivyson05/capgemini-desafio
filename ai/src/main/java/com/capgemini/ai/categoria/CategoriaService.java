package com.capgemini.ai.categoria;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public List<CategoriaModel> listAll() {
        return categoriaRepository.findAll();
    }

    public CategoriaModel create(CategoriaModel categoriaModel) {
        var categoria = this.categoriaRepository.findByNome(categoriaModel.getNome());
        if (categoria.isPresent()) {
            throw new RuntimeException("Categoria já cadastrada");
        }
        return categoriaRepository.save(categoriaModel);
    }

    public CategoriaModel deleteById(UUID id) {
        var categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        this.categoriaRepository.delete(categoria);
        return categoria;
    }

    public CategoriaModel updateById(String id, CategoriaModel categoriaModel) {
        var categoria = this.categoriaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setNome(categoriaModel.getNome());
        categoria.setDescriscao(categoriaModel.getDescriscao());
        return this.categoriaRepository.save(categoria);
    }
}
