package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.auth.LoginRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.auth.LoginResponseDto;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import com.eventos.senac.apieventos_senac.domain.repository.UsuarioRepository;
import com.eventos.senac.apieventos_senac.application.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação controlle", description = "Gerenciamento de autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Autenticação de usuário", description = "Método para fazer autenticação")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        var usuarioValido = usuarioRepository.existsUsuarioByEmailContainingAndSenhaAndStatusNot(loginRequestDto.email(),
                loginRequestDto.senha(), EnumStatusUsuario.EXCLUIDO);

        if (!usuarioValido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário ou senha inválidos");
        } else {
            var token = tokenService.generateToken(loginRequestDto);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build();
            }
            return ResponseEntity.ok(new LoginResponseDto(token));

        }

    }

}
