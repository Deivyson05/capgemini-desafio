import api from './index';

export interface CadastroData {
  nome: string;
  email: string;
  senha: string;
}

export interface LoginData {
  email: string;
  senha: string;
}

export interface UsuarioResponse {
  id: string;
  email: string;
  nome: string;
  token?: string;
}

export async function cadastroUsuario(data: CadastroData) {
  try {
    const response = await api.post('/usuarios/', {
      nome: data.nome,
      email: data.email,
      senha: data.senha,
    });
    return response.data;
  } catch (error: any) {
    console.error('Erro ao cadastrar usuário:', error);
    throw error.response?.data || { message: 'Erro ao cadastrar usuário' };
  }
}

export async function loginUsuario(data: LoginData) {
  try {
    const response = await api.post('/auth/login', {
      email: data.email,
      password: data.senha,
    });
    const usuario = response.data;
    if (usuario) {
      armazenarUsuario(usuario);
    }
    return usuario;
  } catch (error: any) {
    console.error('Erro ao fazer login:', error);
    throw error.response?.data || { message: 'Erro ao fazer login' };
  }
}

export function obterTokenArmazenado() {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('authToken');
  }
  return null;
}

export function sairUsuario() {
  if (typeof window !== 'undefined') {
    localStorage.removeItem('authToken');
    localStorage.removeItem('usuario');
  }
}

export function obterUsuarioArmazenado() {
  if (typeof window !== 'undefined') {
    const usuario = localStorage.getItem('usuario');
    return usuario ? JSON.parse(usuario) : null;
  }
  return null;
}

export function obterIdUsuario() {
  const usuario = obterUsuarioArmazenado();
  return usuario || null;
}

export function armazenarUsuario(usuario: UsuarioResponse) {
  localStorage.setItem('usuario', JSON.stringify(usuario));
}
