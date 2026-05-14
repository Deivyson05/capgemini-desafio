package com.capgemini.ai.categoria;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/")
    public ResponseEntity<List<CategoriaModel>> listAll() {
        List<CategoriaModel> categoria = categoriaService.listAll();
        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping("/")
    public ResponseEntity<CategoriaModel> create(@RequestBody CategoriaModel categoriaModel) {
        CategoriaModel categoria = this.categoriaService.create(categoriaModel);
        return ResponseEntity.ok().body(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoriaModel> deleteById(@PathVariable String id) {
        CategoriaModel categoria = this.categoriaService.deleteById(UUID.fromString(id));
        return ResponseEntity.ok().body(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaModel> updateById(@PathVariable String id, @RequestBody CategoriaModel categoriaModel) {
        CategoriaModel categoria = this.categoriaService.updateById(id, categoriaModel);
        return ResponseEntity.ok().body(categoria);
    }
}
