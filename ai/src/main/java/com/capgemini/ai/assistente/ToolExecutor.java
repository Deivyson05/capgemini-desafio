package com.capgemini.ai.assistente;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.capgemini.ai.pedido.PedidoService;
import com.capgemini.ai.usuario.UsuarioService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ToolExecutor {
    private final UsuarioService usuarioService;
    private final PedidoService pedidoService;

    public String execute(String toolName, Map<String, Object> args) {
        try {
            return switch (toolName) {
                case "getUsuarioByEmail" : {
                    var usuario = usuarioService.findByEmail((String) args.get("email"));
                    yield "Dados do usuário: " + usuario;
                }
                case "getPedidosByUsuarioId": {
                    var pedidos = pedidoService.findByUsuarioId(UUID.fromString((String) args.get("usuarioId")));
                    yield "Dados dos pedidos: " + pedidos;
                }
                case "cancelarPedido": {
                    var resultado = pedidoService.cancelPedido(UUID.fromString((String) args.get("pedidoId")));
                    yield "Resultado da ferramenta 3: " + resultado;
                }
                default:throw new IllegalArgumentException("Ferramenta não suportada: " + toolName);
            };
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar ferramenta: " + e.getMessage(), e);
        }
    }
}
