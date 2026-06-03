export type Product = {
  id: string;
  name: string;
  price: number;
  originalPrice?: number;
  category: string;
  image: string;
  rating: number;
  reviews: number;
  badge?: string;
  description: string;
};

export type Category = {
  id: string;
  label: string;
  icon: string;
};

export const categories: Category[] = [
  { id: "all", label: "Todos", icon: "✦" },
  { id: "eletronicos", label: "Eletrônicos", icon: "⚡" },
  { id: "moda", label: "Moda", icon: "◈" },
  { id: "casa", label: "Casa & Deco", icon: "⬡" },
  { id: "esportes", label: "Esportes", icon: "◎" },
  { id: "beleza", label: "Beleza", icon: "✿" },
];