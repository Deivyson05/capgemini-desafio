"use client";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { ShoppingCart, Package, HeadphonesIcon, Store, X } from "lucide-react";
import { useState } from "react";
import { useCart } from "@/context/CartContext";
import { criarPedido } from "@/api/pedidos";
import { obterIdUsuario } from "@/api/usuario";

export default function Navbar() {
  const pathname = usePathname();
  const router = useRouter();
  const { items, remove, total, count } = useCart();
  const [cartOpen, setCartOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const nav = [
    { href: "/", label: "Loja", icon: Store },
    { href: "/pedidos", label: "Pedidos", icon: Package },
    { href: "/suporte", label: "Suporte", icon: HeadphonesIcon },
  ];

  const handleCheckout = async () => {
    setError("");
    
    // Recupera o usuário do localStorage
    const usuarioId = typeof window !== 'undefined' ? obterIdUsuario() : null;

    if (!usuarioId) {
      router.push("/login?redirect=checkout");
      return;
    }

    if (items.length === 0) {
      setError("Seu carrinho está vazio");
      return;
    }

    setLoading(true);
    try {
      const produtos = items.map(item => ({
        id: String(item.id),
      }));

      console.log(items);

      console.log(produtos);
      console.log(usuarioId);

      await criarPedido({
        usuario: { id: usuarioId },
        produtos,
      });

      setCartOpen(false);
      router.push("/pedidos");
    } catch (err: any) {
      setError(err.message || "Erro ao finalizar compra");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <nav className="fixed top-0 left-0 right-0 z-100 h-[72px] flex items-center justify-between px-8 bg-black/95 backdrop-blur-sm border-b border-border">
        <Link href="/">
          <span className="font-display text-2xl font-bold text-accent tracking-tighter">
            NOVA
          </span>
        </Link>

        <div className="flex gap-1">
          {nav.map(({ href, label, icon: Icon }) => (
            <Link 
              key={href} 
              href={href} 
              className={`flex items-center gap-2 px-4 py-2 rounded-lg transition-all duration-200 text-sm font-medium ${
                pathname === href 
                  ? "text-accent bg-accent/10" 
                  : "text-muted hover:text-text"
              }`}
            >
              <Icon size={16} />
              <span>{label}</span>
            </Link>
          ))}
        </div>

        <button 
          onClick={() => setCartOpen(true)} 
          className="relative flex items-center gap-2 px-4 py-2.5 bg-surface2 border border-border rounded-xl text-text text-sm font-medium hover:border-accent transition-all duration-200"
        >
          <ShoppingCart size={18} className="text-accent" />
          <span>Carrinho</span>
          {count > 0 && (
            <span className="absolute -top-2 -right-2 w-5 h-5 bg-accent text-bg text-xs font-bold rounded-full flex items-center justify-center">
              {count}
            </span>
          )}
        </button>
      </nav>

      {/* Cart Drawer */}
      {cartOpen && (
        <div className="fixed inset-0 z-200 animate-fade-in">
          <div onClick={() => setCartOpen(false)} className="absolute inset-0 bg-black/60" />
          <div className="absolute right-0 top-0 bottom-0 w-[420px] bg-surface border-l border-border flex flex-col p-6 animate-fade-up">
            <div className="flex justify-between items-center mb-6">
              <h2 className="font-display text-2xl">Carrinho</h2>
              <button 
                onClick={() => setCartOpen(false)} 
                className="bg-transparent border-none text-muted cursor-pointer hover:text-text transition-colors"
              >
                <X size={22} />
              </button>
            </div>

            {items.length === 0 ? (
              <div className="flex-1 flex items-center justify-center text-muted flex-col gap-4">
                <ShoppingCart size={48} strokeWidth={1} />
                <p>Seu carrinho está vazio</p>
              </div>
            ) : (
              <>
                <div className="flex-1 overflow-y-auto flex flex-col gap-4">
                  {items.map(item => (
                    <div 
                      key={item.id} 
                      className="flex gap-4 p-4 bg-surface2 rounded-xl border border-border"
                    >
                      <img 
                        src={item.image} 
                        alt={item.name} 
                        className="w-16 h-16 rounded-lg object-cover flex-shrink-0" 
                      />
                      <div className="flex-1">
                        <p className="font-medium mb-1 text-sm">{item.name}</p>
                        <p className="text-accent font-semibold">
                          {(item.price * item.qty).toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}
                        </p>
                        <p className="text-muted text-xs">Qtd: {item.qty}</p>
                      </div>
                      <button 
                        onClick={() => remove(item.id)} 
                        className="bg-transparent border-none text-muted cursor-pointer hover:text-text transition-colors"
                      >
                        <X size={16} />
                      </button>
                    </div>
                  ))}
                </div>
                <div className="border-t border-border pt-6 mt-4">
                  {error && (
                    <div className="p-3 rounded-lg bg-red-500/10 text-red-400 text-xs border border-red-500/30 mb-4">
                      {error}
                    </div>
                  )}
                  <div className="flex justify-between mb-4">
                    <span className="text-muted">Total</span>
                    <span className="font-bold text-lg text-accent">
                      {total.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}
                    </span>
                  </div>
                  <button
                    onClick={handleCheckout}
                    disabled={loading}
                    className="btn-primary w-full py-4 rounded-xl border-none text-base cursor-pointer disabled:opacity-70"
                  >
                    {loading ? "Processando..." : "Finalizar Compra"}
                  </button>
                </div>
              </>
            )}
          </div>
        </div>
      )}
    </>
  );
}
