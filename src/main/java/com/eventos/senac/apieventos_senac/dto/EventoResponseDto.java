package com.eventos.senac.apieventos_senac.dto;

import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;

public record EventoResponseDto(Long id, String nome, String data, int capacidadeMaxima, int inscritos, Long organizadorId,
                                String instituicao, String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                String paraninfo, String orador, Boolean temCerimonialista, String localCerimonia) {

    public static EventoResponseDto fromEvento(EventoFormatura evento) {
        return new EventoResponseDto(evento.getId(), evento.getNome(), evento.getData().toString(), evento.getCapacidadeMaxima(),
                evento.getInscritos(), evento.getOrganizador().getId(), evento.getInstituicao(), evento.getCurso(),
                evento.getAnoFormatura(), evento.getGrauAcademico(), evento.getNumeroFormandos(), evento.getParaninfo(),
                evento.getOrador(), evento.isTemCerimonialista(), evento.getLocalCerimonia());
    }
}

