"use client";

import { useEffect, useState } from "react";
import { Card } from "@/components/ui-dashboard/Card";
import {
  BarChart3,
  CreditCard,
  Package,
  Users,
  AlertCircle,
  PlusCircle,
  FileText,
  ShoppingCart,
} from "lucide-react";

interface DashboardHomeProps {
  usuario: any;
}

interface Alert {
  message: string;
}

interface CardData {
  title: string;
  value: string | number;
  icon: React.ReactNode;
}

interface QuickAction {
  label: string;
  icon: React.ReactNode;
}

export default function DashboardHome({ usuario }: DashboardHomeProps) {
  // Estado para dados do dashboard
  const [cards, setCards] = useState<CardData[]>([]);
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [quickActions, setQuickActions] = useState<QuickAction[]>([]);

  // Simulação de fetch do backend
  useEffect(() => {
    // Aqui futuramente você pode substituir por fetch('/api/dashboard')
    setCards([
      {
        title: "Total Vendas Hoje",
        value: "R$ 1.200",
        icon: <CreditCard className="text-white" />,
      },
      {
        title: "Produtos em Estoque",
        value: 120,
        icon: <Package className="text-white" />,
      },
      {
        title: "Clientes Ativos",
        value: 45,
        icon: <Users className="text-white" />,
      },
      {
        title: "Vendas Semanais",
        value: 320,
        icon: <BarChart3 className="text-white" />,
      },
    ]);

    setAlerts([
      { message: "Produto X está com estoque zerado!" },
      { message: "Vendas da semana abaixo do esperado" },
      { message: "Plano EXPERIMENTAL: 3 dias restantes" },
    ]);

    setQuickActions([
      { label: "Adicionar Produto", icon: <PlusCircle size={24} /> },
      { label: "Registrar Venda", icon: <CreditCard size={24} /> },
      { label: "Abrir Caixa", icon: <ShoppingCart size={24} /> },
      { label: "Ativar Promoção", icon: <BarChart3 size={24} /> },
      { label: "Gerar Relatório", icon: <FileText size={24} /> },
    ]);
  }, []);

  return (
    <div className="p-6 space-y-6">
      <h2 className="text-2xl font-bold text-white">Visão Geral</h2>

      {/* Cards principais */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {cards.map((card, idx) => (
          <Card
            key={idx}
            title={card.title}
            value={card.value}
            icon={card.icon}
            className="bg-[rgba(31,41,55,0.7)]"
          />
        ))}
      </div>

      {/* Alertas Rápidos */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {alerts.map((alert, idx) => (
          <div
            key={idx}
            className="bg-blue-900/30 hover:bg-blue-900/50 text-white p-3 rounded-lg shadow flex items-center gap-3 transition transform hover:scale-105"
          >
            <AlertCircle size={20} className="flex-shrink-0" />
            <span className="font-medium text-sm">{alert.message}</span>
          </div>
        ))}
      </div>

      {/* Atalhos Rápidos */}
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-5 gap-4 mt-4">
        {quickActions.map((action, idx) => (
          <button
            key={idx}
            className="bg-blue-900/30 hover:bg-blue-900/50 text-white p-3 rounded-lg flex flex-col items-center justify-center transition"
            onClick={() => console.log(`Ação: ${action.label}`)}
          >
            {action.icon}
            <span className="text-xs mt-1 font-semibold">{action.label}</span>
          </button>
        ))}
      </div>

      {/* Placeholder de gráficos */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-6">
        <div className="bg-gray-800/60 text-white p-4 rounded shadow">
          <p className="font-medium mb-2">Vendas por método de pagamento</p>
          <div className="h-40 bg-gray-700/30 rounded flex items-center justify-center">
            Gráfico (pizza ou barras)
          </div>
        </div>

        <div className="bg-gray-800/60 text-white p-4 rounded shadow">
          <p className="font-medium mb-2">Vendas por produto</p>
          <div className="h-40 bg-gray-700/30 rounded flex items-center justify-center">
            Gráfico
          </div>
        </div>

        <div className="bg-gray-800/60 text-white p-4 rounded shadow md:col-span-2">
          <p className="font-medium mb-2">Vendas diárias da semana</p>
          <div className="h-40 bg-gray-700/30 rounded flex items-center justify-center">
            Gráfico de linhas ou barras
          </div>
        </div>
      </div>
    </div>
  );
}
