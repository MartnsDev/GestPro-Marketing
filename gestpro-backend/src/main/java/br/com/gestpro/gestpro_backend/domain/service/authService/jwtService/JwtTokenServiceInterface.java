package br.com.gestpro.gestpro_backend.domain.service.authService.jwtService;

import br.com.gestpro.gestpro_backend.domain.model.Usuario;

public interface JwtTokenServiceInterface {
    String gerarToken(Usuario usuario);
}
