"use client";
import { useState, useMemo, useEffect } from "react";
import { Star, ShoppingCart, Search } from "lucide-react";
import { useCart } from "@/context/CartContext";
import useSWR from "swr";
import Link from "next/link";
import { getProdutos } from "@/api/produtos";

interface Produto {
  id: string;
  nome: string;
  descricao: string;
  preco: number;
  imagemUrl: string;
  categoria: {
    id: string;
    nome: string;
    descricao: string;
  };
}

const fetcher = (url: string) =>
  fetch(url).then(r => r.json()).then(data => {
    if (Array.isArray(data)) return data;
    if (Array.isArray(data.data)) return data.data;
    return [];
  });

export default function HomePage() {
  const [search, setSearch] = useState("");
  const [added, setAdded] = useState<string | null>(null);
  const { add } = useCart();

  const { data: produtosAPI = [], isLoading, error } = useSWR<Produto[]>('/produtos', () => getProdutos());

  const handleAdd = (p: Produto) => {
    add({
      id: p.id,
      name: p.nome,
      price: p.preco,
      category: "eletronicos",
      image: p.imagemUrl,
      rating: 4.5,
      reviews: 0,
      description: p.descricao,
    });
    setAdded(p.id);
    setTimeout(() => setAdded(null), 1000);
  };

  return (
    <div className="mx-auto max-w-7xl px-8 pb-16 pt-8">

      {/* Hero */}
      <div className="animate-fade-up relative mb-10 overflow-hidden rounded-2xl border border-border bg-gradient-to-br from-[#1a1a0e] via-[#1e1e16] to-[#161610] p-14">
        {/* glow */}
        <div className="pointer-events-none absolute -right-20 -top-20 h-80 w-80 rounded-full bg-[radial-gradient(circle,rgba(232,197,71,0.15)_0%,transparent_70%)]" />

        <div className="relative z-10 flex items-start justify-between gap-8">
          <div>
            <p className="mb-3 text-xs font-bold uppercase tracking-widest text-accent">
              Nova Coleção 2026
            </p>
            <h1 className="font-display mb-4 text-[clamp(2rem,4vw,3.2rem)] leading-tight">
              Produtos que definem<br />
              <span className="text-accent">o seu estilo.</span>
            </h1>
            <p className="max-w-sm leading-relaxed text-muted">
              Eletrônicos modernos e inovadores. Frete grátis para todo Brasil acima de R$299.
            </p>
          </div>

          <div className="flex shrink-0 flex-col gap-3">
            <Link href="/login">
              <button className="rounded-lg border border-border bg-surface2 px-6 py-2.5 text-sm font-medium text-text transition-all hover:border-accent hover:bg-accent/10">
                Entrar
              </button>
            </Link>
            <Link href="/signup">
              <button className="rounded-lg border-none bg-accent px-6 py-2.5 text-sm font-semibold text-bg transition-all hover:opacity-90">
                Cadastrar
              </button>
            </Link>
          </div>
        </div>
      </div>

      {/* Search */}
      <div className="mb-6">
        <div className="relative max-w-md">
          <Search
            size={16}
            className="absolute left-4 top-1/2 -translate-y-1/2 text-muted"
          />
          <input
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="Buscar produtos..."
            className="w-full rounded-xl border border-border bg-surface2 py-3 pl-11 pr-4 text-sm text-text outline-none transition-colors focus:border-accent"
          />
        </div>
      </div>

      {/* States */}
      {isLoading && (
        <p className="py-16 text-center text-muted">Carregando produtos...</p>
      )}

      {error && (
        <p className="py-16 text-center text-danger">
          Erro ao carregar produtos. Verifique a conexão com o servidor.
        </p>
      )}

      {/* Grid */}
      {!isLoading && !error && (
        <>
          <div className="grid gap-5 [grid-template-columns:repeat(auto-fill,minmax(260px,1fr))]">
            {produtosAPI.map((product, i) => (
              <div
                key={product.id}
                className="card-hover overflow-hidden rounded-2xl border border-border bg-surface"
                style={{ animation: `fadeUp 0.4s ease ${i * 0.04}s both` }}
              >
                <img
                  src={product.imagemUrl}
                  alt={product.nome}
                  className="h-56 w-full object-cover"
                />

                <div className="p-5">
                  <p className="mb-1 text-xs uppercase tracking-wide text-muted">
                    {product.categoria?.nome || "Smartphone"}
                  </p>
                  <h3 className="mb-1 text-sm font-semibold text-text">
                    {product.nome}
                  </h3>
                  <p className="mb-3 line-clamp-2 text-xs leading-relaxed text-muted">
                    {product.descricao}
                  </p>

                  <div className="mb-4 flex items-center gap-1">
                    <Star size={13} fill="currentColor" className="text-accent" />
                    <span className="text-xs font-semibold">4.8</span>
                    <span className="text-xs text-muted">(245)</span>
                  </div>

                  <div className="flex items-center justify-between">
                    <p className="text-lg font-bold text-accent">
                      {product.preco.toLocaleString("pt-BR", {
                        style: "currency",
                        currency: "BRL",
                      })}
                    </p>
                    <button
                      onClick={() => handleAdd(product)}
                      className="btn-primary flex items-center gap-2 rounded-lg px-4 py-2 text-xs"
                    >
                      <ShoppingCart size={15} />
                      {added === product.id ? "✓" : "Adicionar"}
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {produtosAPI.length === 0 && (
            <p className="py-16 text-center text-lg text-muted">
              Nenhum produto encontrado.
            </p>
          )}
        </>
      )}
    </div>
  );
}