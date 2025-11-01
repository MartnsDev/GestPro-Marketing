package br.com.gestpro.gestpro_backend.api.dto.modules.dashboard;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record DashboardVisaoGeralResponse(
        BigDecimal totalVendasHoje,
        Long produtosEmEstoque,
        Long produtosZerados,
        Long clientesAtivos,
        Long vendasSemana,
        Map<String, PlanoDTO> planoExperimental,
        List<String> alertas
) {}
