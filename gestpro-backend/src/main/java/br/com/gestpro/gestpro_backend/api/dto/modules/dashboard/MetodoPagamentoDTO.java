package br.com.gestpro.gestpro_backend.api.dto.modules.dashboard;

import lombok.Data;

@Data
public class MetodoPagamentoDTO {
    private String metodo;
    private long quantidade; // use long para não ter problemas com COUNT(v)

    public MetodoPagamentoDTO(String metodo, long quantidade) {
        this.metodo = metodo;
        this.quantidade = quantidade;
    }

}
