package br.com.gestpro.gestpro_backend.domain.service.authService;

import br.com.gestpro.gestpro_backend.api.dto.AuthDTO.LoginResponse;
import br.com.gestpro.gestpro_backend.domain.model.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAuthenticationService {

    //Cadastro Manual Operation
    Usuario cadastrarManual(String nome, String email, String senha, MultipartFile foto, String baseUrl, String path) throws IOException;
    //Confirmar Email Operation
    boolean confirmarEmail(String token);
    //Login Manual Operation
    LoginResponse loginManual(String email, String senha, String path);
    //Login Google Operation
    Usuario loginOrRegisterGoogle(String email, String nome, String foto);
    //Gerar Token
    String gerarToken(Usuario usuario);
    //Salvar Foto Upload
    String salvarFoto(MultipartFile foto) throws IOException;
    //Atualizar Plano
    Usuario atualizarPlano(String email);
}
