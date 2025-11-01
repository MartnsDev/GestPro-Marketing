package br.com.gestpro.gestpro_backend.domain.repository.auth;

import br.com.gestpro.gestpro_backend.domain.model.auth.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByTokenConfirmacao(String token);


}
