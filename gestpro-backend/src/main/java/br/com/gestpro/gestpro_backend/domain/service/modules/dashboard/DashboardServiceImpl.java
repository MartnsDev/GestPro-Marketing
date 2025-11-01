package br.com.gestpro.gestpro_backend.domain.service.modules.dashboard;

import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.MetodoPagamentoDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.PlanoDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.ProdutoVendasDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.VendasDiariasDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardServiceInterface {

    private final VisaoGeralOperation visaoGeralOperation;
    private final GraficoServiceOperation graficoServiceOperation;

    public DashboardServiceImpl(VisaoGeralOperation visaoGeralOperation,
                                GraficoServiceOperation graficoServiceOperation) {
        this.visaoGeralOperation = visaoGeralOperation;
        this.graficoServiceOperation = graficoServiceOperation;
    }


    @Override
    public BigDecimal totalVendasHoje(String emailUsuario) {
        return visaoGeralOperation.totalVendasHoje(emailUsuario);
    }

    @Override
    public Long produtosEmEstoque(String emailUsuario) {
        return visaoGeralOperation.produtosEmEstoque(emailUsuario);
    }

    @Override
    public Long produtosZerados(String emailUsuario) {
        return visaoGeralOperation.produtosZerados(emailUsuario);
    }

    @Override
    public Long clientesAtivos(String emailUsuario) {
        return visaoGeralOperation.clientesAtivos(emailUsuario);
    }

    @Override
    public Long vendasSemana(String emailUsuario) {
        return visaoGeralOperation.vendasSemana(emailUsuario);
    }

    @Override
    public List<String> alertasProdutosZerados(String emailUsuario) {
        return visaoGeralOperation.alertasProdutosZerados(emailUsuario);
    }

    @Override
    public List<String> alertasVendasSemana(String emailUsuario) {
        return visaoGeralOperation.alertasVendasSemana(emailUsuario);
    }

    @Override
    public Map<String, PlanoDTO> planoUsuarioLogado(String emailUsuario) {
        return visaoGeralOperation.planoUsuarioLogado(emailUsuario);
    }

    @Override
    public List<MetodoPagamentoDTO> vendasPorMetodoPagamento(String emailUsuario) {
        return graficoServiceOperation.vendasPorMetodoPagamento(emailUsuario);
    }

    @Override
    public List<ProdutoVendasDTO> vendasPorProduto(String emailUsuario) {
        return graficoServiceOperation.vendasPorProduto(emailUsuario);
    }

    @Override
    public List<VendasDiariasDTO> vendasDiariasSemana(String emailUsuario) {
        return graficoServiceOperation.vendasDiariasSemana(emailUsuario);
    }
}
