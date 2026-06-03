"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { Package, ChevronDown, ChevronUp, Truck, CheckCircle2, Clock, XCircle, LogOut } from "lucide-react";
import { obterIdUsuario, obterUsuarioArmazenado, sairUsuario } from "@/api/usuario";
import useSWR from "swr";
import { obterPedidosUsuario, type Pedido } from "@/api/pedidos";

const statusConfig = {
  Preparando: { label: "Preparando", color: "var(--accent)", icon: Clock, bg: "rgba(232,197,71,0.1)" },
  Enviado:    { label: "Enviado", color: "#60A5FA", icon: Truck, bg: "rgba(96,165,250,0.1)" },
  Entregue:   { label: "Entregue", color: "var(--success)", icon: CheckCircle2, bg: "rgba(76,175,125,0.1)" },
  Cancelado:  { label: "Cancelado", color: "var(--danger)", icon: XCircle, bg: "rgba(224,85,85,0.1)" },
};

const fetcher = (url: string) => 
  fetch(url).then(r => r.json()).then(data => {
    if (Array.isArray(data)) return data;
    if (Array.isArray(data.data)) return data.data;
    return [];
  });

export default function PedidosPage() {
  const router = useRouter();
  const [expanded, setExpanded] = useState<string | null>(null);
  const [usuarioId, setUsuarioId] = useState<string | null>(null);
  const usuario = obterUsuarioArmazenado();

  useEffect(() => {
    const id = obterIdUsuario();
    if (!id) {
      router.push("/login");
      return;
    }
    setUsuarioId(id);
  }, [router]);

  const { data: pedidos = [], isLoading, error } = useSWR<Pedido[]>(
    '/pedidos/' + usuarioId,
    () => obterPedidosUsuario()
  );

  const toggle = (id: string) => setExpanded(prev => prev === id ? null : id);

  const handleLogout = () => {
    sairUsuario();
    router.push("/");
  };

  return (
    <div className="mx-auto max-w-3xl px-8 pb-16 pt-10">
      <div className="animate-fade-up mb-10 flex justify-between items-start">
        <div>
          <div className="mb-2 flex items-center gap-3">
            <Package size={22} className="text-accent" />
            <h1 className="font-display text-4xl m-0">Meus Pedidos</h1>
          </div>
          <p className="text-muted">
            {isLoading ? "Carregando..." : `${pedidos.length} pedidos no total`}
          </p>
        </div>
        {usuario && (
          <div className="text-right">
            <p className="mb-2 text-sm text-muted">
              {usuario.nome}
            </p>
            <button
              onClick={handleLogout}
              className="flex items-center gap-2 rounded-lg border border-danger/30 bg-danger/10 px-4 py-2 text-xs font-medium text-danger transition-all hover:border-danger/50 hover:bg-danger/20"
            >
              <LogOut size={14} />
              Sair
            </button>
          </div>
        )}
      </div>

      {isLoading && (
        <div className="px-8 py-12 text-center text-muted">
          <p>Carregando seus pedidos...</p>
        </div>
      )}

      {error && (
        <div className="mb-8 rounded-xl border border-danger/30 bg-danger/10 p-6 text-danger">
          Erro ao carregar pedidos. Verifique sua conexão.
        </div>
      )}

      {!isLoading && !error && pedidos.length === 0 && (
        <div className="px-8 py-12 text-center text-muted">
          <p className="text-lg">Você não possui pedidos ainda.</p>
        </div>
      )}

      {!isLoading && !error && pedidos.length > 0 && (
        <div className="flex flex-col gap-4">
          {pedidos.map((order, i) => {
            const s = statusConfig[order.status as keyof typeof statusConfig];
            const StatusIcon = s?.icon || Clock;
            const isOpen = expanded === order.id;

            return (
              <div 
                key={order.id} 
                className="overflow-hidden rounded-2xl border border-border bg-surface transition-colors"
                style={{ animation: `fadeUp 0.35s ease ${i * 0.06}s both` }}
              >
                <button 
                  onClick={() => toggle(order.id)} 
                  className="w-full bg-none border-none cursor-pointer px-6 py-5 text-left font-inherit flex items-center gap-4 hover:bg-surface2/50 transition-colors"
                >
                  <div 
                    className="flex h-11 w-11 items-center justify-center rounded-lg flex-shrink-0"
                    style={{ background: s?.bg }}
                  >
                    <StatusIcon size={20} color={s?.color} />
                  </div>

                  <div className="flex-1">
                    <div className="mb-1 flex items-center gap-2">
                      <span className="font-semibold text-text">{order.id}</span>
                      <span 
                        className="rounded-full px-2.5 py-0.5 text-xs font-semibold"
                        style={{ background: s?.bg, color: s?.color }}
                      >
                        {s?.label}
                      </span>
                    </div>
                    <p className="text-xs text-muted">
                      {order.createdAt} · Entrega prevista: {order.previstoEntrega}
                    </p>
                  </div>

                  <div className="text-muted ml-2">
                    {isOpen ? <ChevronUp size={18} /> : <ChevronDown size={18} />}
                  </div>
                </button>

                {isOpen && (
                  <div className="border-t border-border px-6 py-5 animate-fade-in">
                    <div className="mb-5 flex flex-col gap-3">
                      {order.produtos && order.produtos.length > 0 ? (
                        order.produtos.map((item, j) => (
                          <div key={j} className="flex items-center gap-4">
                            <img
                              className="flex h-14 w-14 items-center justify-center rounded-lg border border-border bg-surface2 text-xs text-muted flex-shrink-0"
                              src={item.imagemUrl || "/placeholder-image.jpg"}
                              alt={item.nome || "Produto"}
                            />
                            <div className="flex-1">
                              <p className="text-sm font-medium">{item.nome || "Produto"}</p>
                              <p className="text-xs text-muted">ID: {item.id}</p>
                            </div>
                          </div>
                        ))
                      ) : (
                        <p className="text-sm text-muted">Nenhum produto neste pedido</p>
                      )}
                    </div>

                    <div className="mb-5 rounded-lg bg-surface2 p-4">
                      <p className="mb-2 text-xs text-muted">
                        Status: <span className="font-semibold text-text">{s?.label}</span>
                      </p>
                      <p className="mb-2 text-xs text-muted">
                        Data do pedido: <span className="font-semibold text-text">{order.createdAt}</span>
                      </p>
                      <p className="text-xs text-muted">
                        Entrega prevista: <span className="font-semibold text-text">{order.previstoEntrega}</span>
                      </p>
                    </div>

                    <div className="flex justify-end gap-2">
                      <button className="btn-primary flex items-center gap-2 rounded-lg px-4 py-2 text-xs border-none">
                        Ver detalhes
                      </button>
                    </div>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
