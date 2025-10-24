package br.com.gestpro.gestpro_backend.domain.model;

import br.com.gestpro.gestpro_backend.domain.model.enums.StatusAcesso;
import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    @Column(name = "foto_google")
    private String foto; // foto obtida via login Google

    @Column
    private String fotoUpload; // caminho da foto enviada manualmente

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plano", nullable = false)
    private TipoPlano tipoPlano = TipoPlano.EXPERIMENTAL;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_primeiro_login")
    private LocalDateTime dataPrimeiroLogin;

    @Column(name = "data_assinatura_plus")
    private LocalDateTime dataAssinaturaPlus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_acesso", nullable = false)
    private StatusAcesso statusAcesso = StatusAcesso.ATIVO;

    @Column(nullable = false)
    private boolean emailConfirmado = false;

    @Column
    private String tokenConfirmacao;

    @Column
    private LocalDateTime dataEnvioConfirmacao;

    @Column
    private String codigoRecuperacao;

    // 🆕 Campo necessário para controle do login Google
    @Column(name = "login_google", nullable = false)
    private boolean loginGoogle = false;

    // ==============================
    // Regras automáticas de persistência
    // ==============================
    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (dataPrimeiroLogin == null) {
            dataPrimeiroLogin = LocalDateTime.now();
        }
        if (statusAcesso == null) {
            statusAcesso = StatusAcesso.ATIVO;
        }
        if (tipoPlano == null) {
            tipoPlano = TipoPlano.EXPERIMENTAL;
        }

        // Define data da assinatura se for plano PLUS
        if (tipoPlano == TipoPlano.ASSINANTE && dataAssinaturaPlus == null) {
            dataAssinaturaPlus = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (tipoPlano == TipoPlano.ASSINANTE && dataAssinaturaPlus == null) {
            dataAssinaturaPlus = LocalDateTime.now();
        }
    }
}
