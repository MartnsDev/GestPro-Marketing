package br.com.gestpro.gestpro_backend.domain.service.authService;


import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import br.com.gestpro.gestpro_backend.domain.model.enums.StatusAcesso;
import br.com.gestpro.gestpro_backend.domain.model.enums.TipoPlano;
import br.com.gestpro.gestpro_backend.domain.repository.UsuarioRepository;
import br.com.gestpro.gestpro_backend.infra.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VerificarPlanoOperation {

    private final UsuarioRepository usuarioRepository;

    public VerificarPlanoOperation(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(Usuario usuario) {
        if (usuario.getTipoPlano() == TipoPlano.EXPERIMENTAL) {
            if (usuario.getDataPrimeiroLogin() == null) {
                usuario.setDataPrimeiroLogin(LocalDateTime.now());
                usuarioRepository.save(usuario);
            } else if (LocalDateTime.now().isAfter(usuario.getDataPrimeiroLogin().plusDays(7))) {
                usuario.setStatusAcesso(StatusAcesso.INATIVO);
                usuarioRepository.save(usuario);
                throw new ApiException(
                        "Período experimental expirado. É necessário pagamento.",
                        HttpStatus.FORBIDDEN,
                        "/pagamento"
                );
            }
        } else if (usuario.getTipoPlano() == TipoPlano.ASSINANTE) {
            if (usuario.getDataAssinaturaPlus() != null &&
                    LocalDateTime.now().isAfter(usuario.getDataAssinaturaPlus().plusDays(30))) {
                usuario.setStatusAcesso(StatusAcesso.INATIVO);
                usuarioRepository.save(usuario);
                throw new ApiException(
                        "Assinatura expirada. É necessário pagamento.",
                        HttpStatus.FORBIDDEN,
                        "/pagamento"
                );
            }
        }

        // Nenhum redirecionamento aqui; só lança exceção se estiver inativo
        if (usuario.getStatusAcesso() == StatusAcesso.INATIVO) {
            throw new ApiException(
                    "Usuário inativo. Redirecionar para pagamento.",
                    HttpStatus.FORBIDDEN,
                    "/pagamento"
            );
        }
    }
}
