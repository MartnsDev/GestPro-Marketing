package br.com.gestpro.gestpro_backend.api.dto.modules.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdutoVendasDTO {
    private String produto;
    private long quantidade;

    // getters e setters...
}
