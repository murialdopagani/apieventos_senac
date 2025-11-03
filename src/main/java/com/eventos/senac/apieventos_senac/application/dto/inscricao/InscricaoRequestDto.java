package com.eventos.senac.apieventos_senac.application.dto.inscricao;

import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumTipoIngresso;
import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDto(@NotNull Long eventoId, String codigoPromocional,
                                  EnumTipoIngresso tipoIngresso) {

}

