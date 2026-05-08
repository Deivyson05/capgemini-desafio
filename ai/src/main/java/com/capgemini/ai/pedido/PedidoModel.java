package com.capgemini.ai.pedido;

import java.util.List;

import com.capgemini.ai.produto.ProdutoModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;
    
    @OneToMany(mappedBy = "pedido")
    private List<ProdutoModel> produtos;
}
