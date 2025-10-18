package com.eventos.senac.apieventos_senac.application.dto.inscricao;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDto(@NotNull Long usuarioId) {
}

