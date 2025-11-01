package br.com.gestpro.gestpro_backend.domain.repository.modules;

import br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.ProdutoVendasDTO;
import br.com.gestpro.gestpro_backend.domain.model.enums.FormaDePagamento;
import br.com.gestpro.gestpro_backend.domain.model.modules.caixa.Caixa;
import br.com.gestpro.gestpro_backend.domain.model.modules.venda.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    // ----------------------------
    // MÉTODOS AUTOMÁTICOS
    // ----------------------------

    List<Venda> findByCaixaId(Long idCaixa);

    List<Venda> findByCaixa(Caixa caixa);

    Long countByDataVendaBetweenAndUsuarioEmail(LocalDateTime inicio, LocalDateTime fim, String emailUsuario);

    long countByFormaPagamentoAndUsuarioEmail(FormaDePagamento formaPagamento, String email);

    // ----------------------------
    // MÉTODOS COM @QUERY (AGREGAÇÃO / JOIN)
    // ----------------------------

    // Vendas diárias de um usuário (query nativa)
    @Query(value = "SELECT DAYOFWEEK(data_venda) as diaSemana, COUNT(*) as total " +
            "FROM venda " +
            "WHERE usuario_id = :usuarioId AND data_venda BETWEEN :inicio AND :fim " +
            "GROUP BY DAYOFWEEK(data_venda)", nativeQuery = true)
    List<Object[]> countVendasDiariasRawPorUsuario(@Param("inicio") LocalDateTime inicio,
                                                   @Param("fim") LocalDateTime fim,
                                                   @Param("usuarioId") Long usuarioId);

    // Total de vendas por período e usuário
    @Query("SELECT COALESCE(SUM(v.valorFinal), 0) " +
            "FROM Venda v " +
            "WHERE v.dataVenda BETWEEN :inicio AND :fim " +
            "AND v.usuario.email = :email")
    BigDecimal somarVendasPorUsuarioEPeriodo(@Param("inicio") LocalDateTime inicio,
                                             @Param("fim") LocalDateTime fim,
                                             @Param("email") String email);

    // Exemplo correto se você quer contar vendas por produto e usuário
    @Query("""
                SELECT new br.com.gestpro.gestpro_backend.api.dto.modules.dashboard.ProdutoVendasDTO(
                    i.produto.nome, SUM(i.quantidade)
                )
                FROM Venda v
                JOIN v.itens i
                WHERE v.usuario.email = :email
                GROUP BY i.produto.nome
            """)
    List<ProdutoVendasDTO> countVendasPorProdutoAndUsuario(@Param("email") String email);


    // Contagem de vendas de um produto específico para um usuário (join na coleção)
    @Query("SELECT COUNT(v) FROM Venda v JOIN v.itens i " +
            "WHERE i.produto.id = :produtoId AND v.usuario.email = :emailUsuario")
    Long countByProdutoIdAndUsuarioEmail(@Param("produtoId") Long produtoId,
                                         @Param("emailUsuario") String emailUsuario);
}
