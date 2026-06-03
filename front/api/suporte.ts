import api from './index';

interface Request {
    message: string;
    sessionId: string;
}

export async function enviarMensagem(request: Request) {
    try {
        const response = await api.post('/assistente/mensagem', request);
        return response.data;
    } catch (error: any) {
        console.error('Erro ao enviar mensagem:', error);
        throw error.response?.data || { message: 'Erro ao enviar mensagem' };
    }
}