"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

type MetodoPagamento = "pix" | "debito" | "boleto";

export default function PagamentoPage() {
  const [pagando, setPagando] = useState(false);
  const [metodo, setMetodo] = useState<MetodoPagamento>("pix");

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [telefone, setTelefone] = useState("");
  const [cpf, setCpf] = useState("");
  const [sucesso, setSucesso] = useState(false);

  async function handlePagamento() {
    if (!nome || !email || !telefone || !cpf) {
      alert("Preencha todos os campos obrigatórios.");
      return;
    }

    setPagando(true);

    setTimeout(() => {
      setPagando(false);
      setSucesso(true);
    }, 1500);
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-[#f7f8fc] to-[#e0e5f2] flex items-center justify-center p-6 relative overflow-hidden">
      {/* Fundo decorativo */}
      <div className="absolute top-0 left-0 w-full h-full bg-[url('/public/bg-loja-pattern.png')] bg-cover bg-center opacity-10 pointer-events-none"></div>

      <div className="max-w-6xl w-full grid md:grid-cols-2 gap-12 z-10">
        {/* Formulário */}
        <motion.div
          initial={{ opacity: 0, x: -50 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.8 }}
          className="bg-white shadow-2xl rounded-3xl p-12 flex flex-col gap-6"
        >
          <h1 className="text-4xl font-bold text-gray-800 mb-2">
            Plano Premium
          </h1>
          <p className="text-gray-600 mb-6">
            Complete seus dados e escolha a forma de pagamento para liberar o
            acesso completo ao GestPro.
          </p>

          <div className="grid grid-cols-1 gap-4">
            <input
              type="text"
              placeholder="Nome completo"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              className="border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-green-400 outline-none transition"
            />
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-green-400 outline-none transition"
            />
            <input
              type="tel"
              placeholder="Telefone"
              value={telefone}
              onChange={(e) => setTelefone(e.target.value)}
              className="border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-green-400 outline-none transition"
            />
            <input
              type="text"
              placeholder="CPF"
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
              className="border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-green-400 outline-none transition"
            />
          </div>

          <div className="mb-6">
            <p className="font-semibold mb-2">Método de pagamento</p>
            <div className="flex gap-4">
              {["pix", "debito", "boleto"].map((m) => (
                <button
                  key={m}
                  onClick={() => setMetodo(m as MetodoPagamento)}
                  className={`flex-1 py-3 rounded-2xl font-semibold transition ${
                    metodo === m
                      ? "bg-green-600 text-white shadow-lg"
                      : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                >
                  {m === "pix"
                    ? "Pix"
                    : m === "debito"
                    ? "Cartão Débito"
                    : "Boleto"}
                </button>
              ))}
            </div>
          </div>

          {/* Campos de pagamento */}
          <div className="mb-6">
            {metodo === "pix" && (
              <div className="p-4 bg-green-50 rounded-2xl border border-green-200">
                <p className="text-green-700 font-medium mb-2">
                  Pix — Pagamento Instantâneo
                </p>
                <p className="text-gray-600 text-sm">
                  Chave Pix: 00.000.000/0001-00
                </p>
                <p className="text-gray-600 text-sm">Valor: R$ 29,90</p>
              </div>
            )}
            {metodo === "debito" && (
              <div className="p-4 bg-blue-50 rounded-2xl border border-blue-200">
                <p className="text-blue-700 font-medium mb-2">Cartão Débito</p>
                <input
                  type="text"
                  placeholder="Número do cartão"
                  className="border rounded-xl px-3 py-2 w-full mb-2"
                />
                <div className="flex gap-2">
                  <input
                    type="text"
                    placeholder="Validade MM/AA"
                    className="border rounded-xl px-3 py-2 flex-1"
                  />
                  <input
                    type="text"
                    placeholder="CVV"
                    className="border rounded-xl px-3 py-2 w-24"
                  />
                </div>
                <input
                  type="text"
                  placeholder="Nome do titular"
                  className="border rounded-xl px-3 py-2 w-full mt-2"
                />
              </div>
            )}
            {metodo === "boleto" && (
              <div className="p-4 bg-yellow-50 rounded-2xl border border-yellow-200">
                <p className="text-yellow-700 font-medium mb-2">
                  Boleto Bancário
                </p>
                <p className="text-gray-600 text-sm">Valor: R$ 29,90</p>
                <p className="text-gray-600 text-sm">Vencimento: em 3 dias</p>
              </div>
            )}
          </div>

          <button
            onClick={handlePagamento}
            disabled={pagando || sucesso}
            className="w-full bg-gradient-to-r from-green-500 to-green-600 text-white font-bold py-3 rounded-2xl shadow-lg hover:shadow-xl transition-all disabled:opacity-50"
          >
            {pagando
              ? "Processando..."
              : sucesso
              ? "Pagamento Concluído ✅"
              : "Finalizar Pagamento"}
          </button>

          <AnimatePresence>
            {sucesso && (
              <motion.div
                initial={{ scale: 0 }}
                animate={{ scale: 1, rotate: 360 }}
                exit={{ scale: 0 }}
                transition={{ duration: 0.8 }}
                className="mt-4 text-center text-green-600 font-bold text-lg"
              >
                🎉 Pagamento realizado com sucesso!
              </motion.div>
            )}
          </AnimatePresence>
        </motion.div>

        {/* Resumo do Plano */}
        <motion.div
          initial={{ opacity: 0, x: 50 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.8 }}
          className="bg-white shadow-2xl rounded-3xl p-10 flex flex-col gap-6"
        >
          <h2 className="text-3xl font-bold text-gray-800">Resumo do Plano</h2>
          <p className="text-gray-600 text-sm">
            Plano: <strong>Premium Mensal</strong>
          </p>
          <p className="text-gray-600 text-sm">
            Valor: <strong>R$ 29,90 / mês</strong>
          </p>
          <p className="text-gray-600 text-sm">
            Recursos: controle de estoque, vendas, clientes e relatórios.
          </p>

          <h3 className="font-semibold text-gray-700 mt-4">Benefícios</h3>
          <ul className="list-disc list-inside text-gray-600 text-sm">
            <li>Suporte via chat</li>
            <li>Backup diário</li>
            <li>Integração com PDV</li>
          </ul>

          <h3 className="font-semibold text-gray-700 mt-4">Ajuda</h3>
          <p className="text-gray-500 text-xs">
            Página simulada para testes. Em produção, integre com gateways reais
            como Stripe, MercadoPago ou PagSeguro.
          </p>
        </motion.div>
      </div>
    </div>
  );
}
