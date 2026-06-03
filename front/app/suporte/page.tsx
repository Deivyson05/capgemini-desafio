"use client";
import { useState, useRef, useEffect } from "react";
import { Send, HeadphonesIcon, Bot, User, Zap, Package, RefreshCw, CreditCard } from "lucide-react";
import { enviarMensagem } from "@/api/suporte";

type Message = { role: "user" | "agent"; text: string; time: string };

const quickOptions = [
  { icon: Package, label: "Rastrear pedido" },
  { icon: Zap, label: "Prazo de entrega" },
];

function formatTime() {
  return new Date().toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" });
}

export default function SuportePage() {
  const [messages, setMessages] = useState<Message[]>([
    { role: "agent", text: "Olá! Sou a Ava, sua assistente da NOVA Store. Como posso te ajudar hoje? 😊", time: formatTime() }
  ]);
  const [input, setInput] = useState("");
  const [loading, setLoading] = useState(false);
  const bottomRef = useRef<HTMLDivElement>(null);
  const sessionId = useRef(crypto.randomUUID());

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages, loading]);

  const sendMessage = async (text: string) => {
    if (!text.trim() || loading) return;
    setMessages(prev => [...prev, { role: "user", text: text.trim(), time: formatTime() }]);
    setInput("");
    setLoading(true);

    try {
      const res = await enviarMensagem({ sessionId: sessionId.current, message: text });

      const reply = res.reply || "Desculpe, não consegui processar sua mensagem.";
      console.log("resposta suporte:", res);
      setMessages(prev => [...prev, { role: "agent", text: reply, time: formatTime() }]);
    } catch {
      setMessages(prev => [...prev, { role: "agent", text: "Ops, tive um problema de conexão. Tente novamente!", time: formatTime() }]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto max-w-3xl px-8 pb-16 pt-10">
      <div className="animate-fade-up mb-6">
        <div className="mb-2 flex items-center gap-3">
          <HeadphonesIcon size={22} className="text-accent" />
          <h1 className="font-display text-4xl m-0">Suporte</h1>
        </div>
        <p className="text-muted">Atendimento disponível 24h por IA</p>
      </div>

      {/* Agent card */}
      <div className="animate-fade-up mb-6 rounded-2xl border border-accent/25 bg-gradient-to-r from-[#1a1a0e] to-[#161610] p-5 flex items-center gap-4">
        <div className="flex h-12 w-12 items-center justify-center rounded-full bg-gradient-to-br from-accent to-accent-alt flex-shrink-0">
          <Bot size={22} className="text-bg" />
        </div>
        <div>
          <p className="mb-1 font-semibold">Ava — Assistente Virtual</p>
          <div className="flex items-center gap-2">
            <span className="inline-block h-1.5 w-1.5 rounded-full bg-success" />
            <span className="text-xs text-muted">Online agora</span>
          </div>
        </div>
      </div>

      {/* Quick options */}
      <div className="mb-6 flex flex-wrap gap-2">
        {quickOptions.map(({ icon: Icon, label }) => (
          <button 
            key={label} 
            onClick={() => sendMessage(label)} 
            className="flex items-center gap-2 rounded-full border border-border bg-surface2 px-4 py-2 text-xs text-muted transition-all hover:border-accent hover:text-accent"
          >
            <Icon size={14} />
            {label}
          </button>
        ))}
      </div>

      {/* Chat window */}
      <div className="overflow-hidden rounded-3xl border border-border bg-surface">
        <div className="flex h-[440px] flex-col gap-4 overflow-y-auto p-6">
          {messages.map((msg, i) => (
            <div 
              key={i} 
              className={`flex gap-2.5 ${msg.role === "user" ? "flex-row-reverse" : "flex-row"} items-end animate-fade-up`}
              style={{ animation: `fadeUp 0.25s ease forwards` }}
            >
              <div 
                className="flex h-8 w-8 items-center justify-center rounded-full flex-shrink-0 border border-border"
                style={{
                  background: msg.role === "agent" ? "linear-gradient(135deg, var(--accent), var(--accent-alt))" : undefined,
                  backgroundColor: msg.role === "user" ? "var(--surface2)" : undefined,
                }}
              >
                {msg.role === "agent" ? 
                  <Bot size={15} className="text-bg" /> : 
                  <User size={15} className="text-muted" />
                }
              </div>

              <div className="max-w-xs">
                <div 
                  className={`rounded-2xl px-4 py-3 text-sm leading-relaxed ${
                    msg.role === "user" 
                      ? "rounded-br-sm border border-accent/20 bg-accent/10" 
                      : "rounded-bl-sm border border-border bg-surface2"
                  }`}
                >
                  {msg.text}
                </div>
                <p className={`mt-1 text-xs text-muted ${msg.role === "user" ? "text-right" : "text-left"}`}>
                  {msg.time}
                </p>
              </div>
            </div>
          ))}

          {loading && (
            <div className="flex items-end gap-2.5">
              <div className="flex h-8 w-8 items-center justify-center rounded-full bg-gradient-to-br from-accent to-accent-alt flex-shrink-0">
                <Bot size={15} className="text-bg" />
              </div>
              <div className="flex gap-1 rounded-2xl rounded-bl-sm border border-border bg-surface2 px-4 py-3">
                <span className="typing-dot" />
                <span className="typing-dot" />
                <span className="typing-dot" />
              </div>
            </div>
          )}

          <div ref={bottomRef} />
        </div>

        {/* Input */}
        <div className="border-t border-border flex gap-3 p-4">
          <input
            value={input}
            onChange={e => setInput(e.target.value)}
            onKeyDown={e => e.key === "Enter" && !e.shiftKey && sendMessage(input)}
            placeholder="Digite sua mensagem..."
            disabled={loading}
            className="flex-1 rounded-xl border border-border bg-surface2 px-4 py-2.5 text-sm text-text outline-none transition-colors focus:border-accent disabled:opacity-50"
          />
          <button
            onClick={() => sendMessage(input)}
            disabled={!input.trim() || loading}
            className="btn-primary flex items-center justify-center rounded-xl border-none px-4 py-2.5 disabled:opacity-50"
          >
            <Send size={18} />
          </button>
        </div>
      </div>
    </div>
  );
}
