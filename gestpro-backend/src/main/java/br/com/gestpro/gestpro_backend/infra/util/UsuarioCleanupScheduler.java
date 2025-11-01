package br.com.gestpro.gestpro_backend.infra.util;

import br.com.gestpro.gestpro_backend.domain.model.auth.Usuario;
import br.com.gestpro.gestpro_backend.domain.repository.auth.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioCleanupScheduler {

    private final UsuarioRepository usuarioRepository;

    /**
     * Executa a cada 1 hora (para manter o sistema limpo)
     * e remove usuários não confirmados há mais de 24h.
     */
    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000) // a cada 1 hora
    public void removerUsuariosNaoConfirmados() {
        LocalDateTime limite = LocalDateTime.now().minusHours(24);

        List<Usuario> usuariosParaRemover = usuarioRepository.findAll().stream()
                .filter(u -> !u.isEmailConfirmado()
                        && u.getSenha() != null // garante que é cadastro manual
                        && u.getDataCriacao().isBefore(limite))
                .toList();

        if (!usuariosParaRemover.isEmpty()) {
            usuarioRepository.deleteAll(usuariosParaRemover);
            log.info("usuários não confirmados foram removidos após 24h.", usuariosParaRemover.size());
        }
    }
}
