package com.eventos.senac.apieventos_senac.dto;

import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;

import java.time.LocalDateTime;

public record EventoFormaturaResponseDto(Long id, String nome, String data, int capacidadeMaxima, int inscritos, Long organizadorId,
                                         String instituicao, String curso, int anoFormatura, String grauAcademico,
                                         int numeroFormandos, String paraninfo, String orador, boolean temCerimonialista,
                                         String localCerimonia) {

    public static EventoFormaturaResponseDto fromEvento(EventoFormatura evento) {
        return new EventoFormaturaResponseDto(
                evento.getId(),
                evento.getNome(),
                evento.getData().toString(),
                evento.getCapacidadeMaxima(),
                evento.getInscritos(),
                evento.getOrganizador().getId(),
                evento.getInstituicao(),
                evento.getCurso(),
                evento.getAnoFormatura(),
                evento.getGrauAcademico(),
                evento.getNumeroFormandos(),
                evento.getParaninfo(),
                evento.getOrador(),
                evento.isTemCerimonialista(),
                evento.getLocalCerimonia()
        );
    }
}

