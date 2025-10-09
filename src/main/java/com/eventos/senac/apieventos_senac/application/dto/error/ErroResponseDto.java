package com.eventos.senac.apieventos_senac.application.dto.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErroResponseDto(int status, String erro, String mensagem, String path, LocalDateTime timestamp) {

    public static ErroResponseDto of(HttpStatus status, String mensagem, String path) {
        return new ErroResponseDto(status.value(), status.getReasonPhrase(), mensagem, path, LocalDateTime.now());
    }

}
