package br.com.gestpro.gestpro_backend.domain.service.modules.dashboard;

import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.MetodoPagamentoDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.PlanoDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.ProdutoVendasDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.VendasDiariasDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DashboardServiceInterface {

    // ------------------ VISÃO GERAL ------------------

    BigDecimal totalVendasHoje(String emailUsuario);

    Long produtosEmEstoque(String emailUsuario);

    Long produtosZerados(String emailUsuario);

    Long clientesAtivos(String emailUsuario);

    Long vendasSemana(String emailUsuario);

    List<String> alertasProdutosZerados(String emailUsuario);

    List<String> alertasVendasSemana(String emailUsuario);

    /**
     * Retorna informações do plano do usuário logado
     */
    Map<String, PlanoDTO> planoUsuarioLogado(String emailUsuario);

    // ------------------ GRÁFICOS ------------------

    /**
     * Retorna a quantidade de vendas por método de pagamento
     */
    List<MetodoPagamentoDTO> vendasPorMetodoPagamento(String emailUsuario);

    /**
     * Retorna a quantidade vendida de cada produto
     */
    List<ProdutoVendasDTO> vendasPorProduto(String emailUsuario);

    /**
     * Retorna o total de vendas por dia da semana
     */
    List<VendasDiariasDTO> vendasDiariasSemana(String emailUsuario);
}
