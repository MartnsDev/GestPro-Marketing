package br.com.gestpro.gestpro_backend.api.dto.AuthDTO;

import br.com.gestpro.gestpro_backend.domain.model.Usuario;

public record AuthResponseDTO(String token, Usuario usuario) {}
