package com.capgemini.ai.pedido;

import java.util.List;
import java.util.UUID;

import com.capgemini.ai.produto.ProdutoModel;
import com.capgemini.ai.usuario.UsuarioModel;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;
    
    @OneToMany(mappedBy = "id")
    private List<ProdutoModel> produtos;
}
