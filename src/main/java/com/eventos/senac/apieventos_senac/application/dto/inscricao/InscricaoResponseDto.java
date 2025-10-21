package com.eventos.senac.apieventos_senac.application.dto.inscricao;

import com.eventos.senac.apieventos_senac.domain.entity.Inscricao;

public record InscricaoResponseDto(Long id, String evento, String inscrito, String statusPresenca, String tipoIngresso) {


    public InscricaoResponseDto(Inscricao inscricao) {
        this(
                inscricao.getId(),
                inscricao.getEvento().getNome(),
                inscricao.getUsuario().getNome(),
                inscricao.getStatusPresenca().name(),
                inscricao.getTipoIngresso().name()
        );
    }
}
