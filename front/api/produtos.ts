import api from './index';

export async function getProdutos() {
    try {
        const response:any = await api.get('/produtos');
        return response.data;
    } catch (error) {
        console.error('Erro ao buscar produtos:', error);
        throw error;
    }
}

export async function getProdutoById(id: string) {
    try {
        const response = await api.get(`/produtos/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Erro ao buscar produto com ID ${id}:`, error);
        throw error;
    }
}