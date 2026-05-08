package com.capgemini.ai.produto;

import com.capgemini.ai.categoria.CategoriaModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descriscao;

    @Column(nullable = false)
    private int preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaModel categoria;
}
