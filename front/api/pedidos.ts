import api from './index';
import { obterIdUsuario } from './usuario';

export interface Produto {
  id: string;
}

export interface PedidoRequest {
  usuario: {
    id: string;
  };
  produtos: Produto[];
}

export interface UsuarioPedido {
  id: string;
  nome: string;
  email: string;
  senha?: string;
}

export interface Pedido {
  id: string;
  usuario: UsuarioPedido;
  produtos: any[];
  createdAt: string;
  previstoEntrega: string;
  status: 'Preparando' | 'Enviado' | 'Entregue' | 'Cancelado';
  aviso: string | null;
}

export async function criarPedido(data: PedidoRequest) {
  try {
    const response = await api.post('/pedidos', data);
    return response.data;
  } catch (error: any) {
    console.error('Erro ao criar pedido:', error);
    throw error.response?.data || { message: 'Erro ao criar pedido' };
  }
}

export async function obterPedidosUsuario() {
  try {
    const response = await api.get(`/pedidos/usuario/${obterIdUsuario()}`);
    const data = response.data;
    if (Array.isArray(data)) return data;
    if (Array.isArray(data.data)) return data.data;
    return [];
  } catch (error: any) {
    console.error('Erro ao buscar pedidos:', error);
    throw error.response?.data || { message: 'Erro ao buscar pedidos' };
  }
}