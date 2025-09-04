package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.LoginRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping("/login")
    @Operation(summary = "Autenticação de usuário", description = "Método para fazer autenticação")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        if (loginRequestDto.email().equals("String") && loginRequestDto.senha().equals("String")) {
            return ResponseEntity.ok(loginRequestDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
