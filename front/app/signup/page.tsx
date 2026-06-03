"use client";
import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { cadastroUsuario } from "@/api/usuario";
import { ArrowLeft } from "lucide-react";

export default function SignupPage() {
  const router = useRouter();
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [confirmaSenha, setConfirmaSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    if (!nome || !email || !senha || !confirmaSenha) {
      setError("Preencha todos os campos");
      setLoading(false);
      return;
    }

    if (senha !== confirmaSenha) {
      setError("As senhas não conferem");
      setLoading(false);
      return;
    }

    if (senha.length < 6) {
      setError("A senha deve ter pelo menos 6 caracteres");
      setLoading(false);
      return;
    }

    try {
      const response = await cadastroUsuario({ nome, email, senha });
      if (response) {
        router.push("/login?success=true");
      }
    } catch (err: any) {
      setError(err.message || "Erro ao cadastrar");
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
            Criar conta
          </h1>
          <p className="mb-8 text-sm text-muted">
            Junte-se à NOVA e aproveite ofertas exclusivas
          </p>

          <form onSubmit={handleSubmit} className="flex flex-col gap-6">
            <div>
              <label className="mb-2 block text-sm font-medium text-text">
                Nome Completo
              </label>
              <input
                type="text"
                value={nome}
                onChange={e => setNome(e.target.value)}
                placeholder="Seu Nome"
                className="w-full rounded-xl border border-border bg-surface2 px-4 py-3 text-sm text-text outline-none transition-colors focus:border-accent"
              />
            </div>

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

            <div>
              <label className="mb-2 block text-sm font-medium text-text">
                Confirmar Senha
              </label>
              <input
                type="password"
                value={confirmaSenha}
                onChange={e => setConfirmaSenha(e.target.value)}
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
              {loading ? "Cadastrando..." : "Criar Conta"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="m-0 text-sm text-muted">
              Já tem conta?{" "}
              <Link href="/login">
                <span className="cursor-pointer font-semibold text-accent transition-colors hover:opacity-80">
                  Faça login
                </span>
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
