package com.capgemini.ai.pedido;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.capgemini.ai.produto.ProdutoModel;
import com.capgemini.ai.usuario.UsuarioModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pedido")
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
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pedido_produto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<ProdutoModel> produtos;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate previstoEntrega;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private String aviso;
}