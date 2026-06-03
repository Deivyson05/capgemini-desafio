# capgemini-desafio

# 🤖 Agente de Suporte Inteligente

Solução de atendimento automatizado com IA que se comunica com clientes de forma simples e amigável, disponível 24 horas por dia. O agente é capaz de identificar clientes, consultar pedidos e resolver problemas de forma autônoma, sem intervenção humana.

---

## ✨ Funcionalidades

- Atendimento automatizado 24/7
- Identificação do cliente por e-mail
- Consulta de pedidos em tempo real
- Cancelamento automático de pedidos com problema logístico
- Histórico de conversa por sessão
- Comunicação natural e amigável via LLM

---

## 🛠️ Tecnologias

### Backend
- **Java 17**
- **Spring Boot**
- **API Groq** (LLM — modelo `llama-3.3-70b-versatile`)

### Frontend
- **Next.js** com **TypeScript**
- **Axios** para comunicação com a API
- **Tailwind CSS** para estilização

### Infraestrutura
- **Docker**
- **Render** (deploy)

---

## 🏗️ Arquitetura

```
Frontend (Next.js)
      │
      │  POST /assistente/mensagem
      │  { message, sessionId }
      ▼
Backend (Spring Boot)
      │
      ├── AssistenteController
      ├── AssistenteService        ← orquestra o fluxo
      ├── GroqClient               ← chama a API do Groq
      ├── SessionHistoryManager    ← mantém histórico por sessão
      ├── PromptBuilder            ← monta o system prompt do agente
      └── ToolExecutor             ← executa as ações (buscar, cancelar)
            │
            ├── UsuarioService
            └── PedidoService
```

---

## 🔄 Fluxo de Atendimento

```
1. Cliente envia mensagem
2. Agente pede e-mail
3. Busca dados do cliente
4. Busca lista de pedidos
5. Cliente informa qual pedido tem problema
6. Agente verifica status e datas
7. Se +10 dias sem atualização → cancela e pede desculpas
8. Se outro problema → responde conforme a situação
```

---

## 🚀 Como rodar localmente

### Pré-requisitos
- Java 21+
- Node.js 18+
- Chave de API da [Groq](https://console.groq.com)

### Backend

```bash
cd ai
cp .env.example .env
# preencha GROQ_API_KEY no .env
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## ⚙️ Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `GROQ_API_KEY` | Chave de acesso à API do Groq |
| `DATABASE_URL` | URL de conexão com o banco de dados |

---

## 🐳 Docker

```bash
cd ai
docker build -t agente-suporte .
docker run -p 8080:8080 --env-file .env agente-suporte
```

---

## 📡 Endpoints

### `POST /assistente/mensagem`

**Request:**
```json
{
  "message": "Olá, estou com um problema no meu pedido",
  "sessionId": "abc123"
}
```

**Response:**
```json
{
  "reply": "Olá! Pode me informar seu e-mail cadastrado?"
}
```

---

## 📁 Estrutura do projeto

```
capgemini-desafio/
├── ai/                         # Backend Spring Boot
│   ├── src/
│   │   └── main/java/com/capgemini/ai/
│   │       ├── assistente/     # Módulo do agente
│   │       ├── pedido/         # Módulo de pedidos
│   │       └── usuario/        # Módulo de usuários
│   ├── Dockerfile
│   └── pom.xml
└── frontend/                   # Frontend Next.js
    ├── src/
    ├── package.json
    └── tailwind.config.ts
```

---

## 👥 Desenvolvido por Deivyson Ricardo Silva dos Santos

Projeto desenvolvido como desafio Capgemini.