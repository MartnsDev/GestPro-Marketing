package br.com.gestpro.gestpro_backend.api.controller.modules;

import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.DashboardVisaoGeralResponse;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.MetodoPagamentoDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.ProdutoVendasDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.VendasDiariasDTO;
import br.com.gestpro.gestpro_backend.domain.service.modules.dashboard.DashboardServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardServiceInterface dashboardService;

    public DashboardController(DashboardServiceInterface dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/visao-geral")
    public ResponseEntity<DashboardVisaoGeralResponse> getVisaoGeral(Authentication authentication) {
        String emailUsuario = authentication.getName();

        List<String> alertas = Stream.concat(
                dashboardService.alertasProdutosZerados(emailUsuario).stream(),
                dashboardService.alertasVendasSemana(emailUsuario).stream()
        ).toList();

        DashboardVisaoGeralResponse response = new DashboardVisaoGeralResponse(
                dashboardService.totalVendasHoje(emailUsuario),
                dashboardService.produtosEmEstoque(emailUsuario),
                dashboardService.produtosZerados(emailUsuario),
                dashboardService.clientesAtivos(emailUsuario),
                dashboardService.vendasSemana(emailUsuario),
                dashboardService.planoUsuarioLogado(emailUsuario),
                alertas
        );

        return ResponseEntity.ok(response);
    }


    // ------------------ GRÁFICOS ------------------

    @GetMapping("/vendas/metodo-pagamento")
    public ResponseEntity<List<MetodoPagamentoDTO>> vendasPorMetodoPagamento(Authentication authentication) {
        String emailUsuario = authentication.getName();

        List<MetodoPagamentoDTO> result = dashboardService.vendasPorMetodoPagamento(emailUsuario);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/vendas/produto")
    public ResponseEntity<List<ProdutoVendasDTO>> vendasPorProduto(Authentication authentication) {
        String emailUsuario = authentication.getName();

        List<ProdutoVendasDTO> result = dashboardService.vendasPorProduto(emailUsuario);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/vendas/diarias")
    public ResponseEntity<List<VendasDiariasDTO>> vendasDiariasSemana(Authentication authentication) {
        String emailUsuario = authentication.getName();
        List<VendasDiariasDTO> result = dashboardService.vendasDiariasSemana(emailUsuario);
        return ResponseEntity.ok(result);
    }
}
