package br.com.gestpro.gestpro_backend.domain.service.modules.dashboard;

import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.MetodoPagamentoDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.ProdutoVendasDTO;
import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.VendasDiariasDTO;
import br.com.gestpro.gestpro_backend.domain.model.enums.FormaDePagamento;
import br.com.gestpro.gestpro_backend.domain.repository.auth.UsuarioRepository;
import br.com.gestpro.gestpro_backend.domain.repository.modules.ProdutoRepository;
import br.com.gestpro.gestpro_backend.domain.repository.modules.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraficoServiceOperation {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public GraficoServiceOperation(VendaRepository vendaRepository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ---------------------------- Gráficos ---------------------------------------------

    /**
     * Vendas por forma de pagamento do usuário
     */
    public List<MetodoPagamentoDTO> vendasPorMetodoPagamento(String emailUsuario) {
        return List.of(
                new MetodoPagamentoDTO("Dinheiro",
                        vendaRepository.countByFormaPagamentoAndUsuarioEmail(FormaDePagamento.DINHEIRO, emailUsuario)),
                new MetodoPagamentoDTO("Cartão",
                        vendaRepository.countByFormaPagamentoAndUsuarioEmail(FormaDePagamento.CARTAO, emailUsuario)),
                new MetodoPagamentoDTO("Pix",
                        vendaRepository.countByFormaPagamentoAndUsuarioEmail(FormaDePagamento.PIX, emailUsuario))
        );
    }


    /**
     * Vendas agregadas por produto do usuário
     */
    public List<ProdutoVendasDTO> vendasPorProduto(String emailUsuario) {
        return produtoRepository.findByUsuarioEmail(emailUsuario)
                .stream()
                .map(prod -> {
                    int total = vendaRepository.countByProdutoIdAndUsuarioEmail(prod.getId(), emailUsuario) != null
                            ? vendaRepository.countByProdutoIdAndUsuarioEmail(prod.getId(), emailUsuario).intValue()
                            : 0;
                    return new ProdutoVendasDTO(prod.getNome(), total);
                })
                .toList();
    }


    /**
     * Vendas diárias da semana do usuário
     */
    @Transactional(readOnly = true)
    public List<VendasDiariasDTO> vendasDiariasSemana(String emailUsuario) {
        // Pega o ID do usuário
        Long usuarioId = usuarioRepository.findByEmail(emailUsuario)
                .map(u -> u.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = hoje.with(DayOfWeek.SUNDAY);

        LocalDateTime inicio = inicioSemana.atStartOfDay();
        LocalDateTime fim = fimSemana.atTime(23, 59, 59);

        List<Object[]> raw = countVendasDiariasRawPorUsuario(inicio, fim, usuarioId);

        // Map: dia da semana -> total de vendas
        Map<Integer, Double> vendasPorDia = raw.stream()
                .collect(Collectors.toMap(
                        o -> ((Number) o[0]).intValue() - 1, // subtrai 1 para 0=Domingo, 6=Sábado
                        o -> ((Number) o[1]).doubleValue()
                ));

        String[] nomesDias = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};

        List<VendasDiariasDTO> result = new ArrayList<>();
        for (int dia = 0; dia <= 6; dia++) {
            double total = vendasPorDia.getOrDefault(dia, 0.0);
            result.add(new VendasDiariasDTO(nomesDias[dia], total));
        }

        return result;
    }

    // Ajuste do método privado
    private List<Object[]> countVendasDiariasRawPorUsuario(LocalDateTime inicio, LocalDateTime fim, Long usuarioId) {
        return vendaRepository.countVendasDiariasRawPorUsuario(inicio, fim, usuarioId);
    }

}
