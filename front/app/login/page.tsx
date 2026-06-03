"use client";
import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { loginUsuario, armazenarUsuario } from "@/api/usuario";
import { ArrowLeft } from "lucide-react";

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    if (!email || !senha) {
      setError("Preencha todos os campos");
      setLoading(false);
      return;
    }

    try {
      const response = await loginUsuario({ email, senha });
      if (response) {
        armazenarUsuario(response);
        router.push("/");
      }
    } catch (err: any) {
      setError(err.message || "Erro ao fazer login");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-[calc(100vh-72px)] items-center justify-center px-8 py-8">
      <div className="w-full max-w-md">
        <Link href="/">
          <button className="mb-8 flex items-center gap-2 bg-none border-none text-muted cursor-pointer text-sm font-medium transition-colors hover:text-text">
            <ArrowLeft size={18} />
            Voltar para home
          </button>
        </Link>

        <div className="mb-8 rounded-3xl border border-border bg-surface p-10">
          <h1 className="m-0 mb-2 text-3xl font-bold">
            Bem-vindo de volta
          </h1>
          <p className="mb-8 text-sm text-muted">
            Faça login na sua conta para continuar
          </p>

          <form onSubmit={handleSubmit} className="flex flex-col gap-6">
            <div>
              <label className="mb-2 block text-sm font-medium text-text">
                Email
              </label>
              <input
                type="email"
                value={email}
                onChange={e => setEmail(e.target.value)}
                placeholder="seu@email.com"
                className="w-full rounded-xl border border-border bg-surface2 px-4 py-3 text-sm text-text outline-none transition-colors focus:border-accent"
              />
            </div>

            <div>
              <label className="mb-2 block text-sm font-medium text-text">
                Senha
              </label>
              <input
                type="password"
                value={senha}
                onChange={e => setSenha(e.target.value)}
                placeholder="••••••••"
                className="w-full rounded-xl border border-border bg-surface2 px-4 py-3 text-sm text-text outline-none transition-colors focus:border-accent"
              />
            </div>

            {error && (
              <div className="rounded-lg border border-red-500/30 bg-red-500/10 px-4 py-3 text-xs text-red-400">
                {error}
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              className="btn-primary rounded-xl border-none px-6 py-3 text-sm font-semibold transition-all disabled:opacity-70"
            >
              {loading ? "Entrando..." : "Entrar"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="m-0 text-sm text-muted">
              Não tem conta?{" "}
              <Link href="/signup">
                <span className="cursor-pointer font-semibold text-accent transition-colors hover:opacity-80">
                  Cadastre-se
                </span>
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
