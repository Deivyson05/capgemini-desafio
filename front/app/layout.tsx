import "./globals.css";
import type { Metadata } from "next";

import Navbar from "@/components/Navbar";
import { CartProvider } from "@/context/CartContext";

export const metadata: Metadata = {
  title: "NOVA — Loja",
  description: "E-commerce moderno e elegante",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="pt-BR">
      <body className="min-h-screen bg-bg text-text">
        <CartProvider>
          <Navbar />
          <main className="pt-[72px]">{children}</main>
        </CartProvider>
      </body>
    </html>
  );
}
