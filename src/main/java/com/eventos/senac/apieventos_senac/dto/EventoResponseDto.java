package com.eventos.senac.apieventos_senac.dto;

import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventoResponseDto(Long id, String nome, String data, int capacidadeMaxima, int inscritos, Long organizadorId,
                                String tipoEvento, EnumStatusEvento status,

                                //Campos específicos de formatura
                                String instituicao, String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                String paraninfo, String orador, Boolean temCerimonialista, String localCerimonia) {

    public static EventoResponseDto fromEvento(Evento evento) {

        //Valores base(comuns a todos os eventos)
        Long id = evento.getId();
        String nome = evento.getNome();
        String data = evento.getData() != null ? evento.getData().toString() : null;
        int capacidadeMaxima = evento.getCapacidadeMaxima();
        int inscritos = evento.getInscritos();
        Long organizadorId = evento.getOrganizador().getId();
        String localCerimonia = evento.getLocalCerimonia() != null ? evento.getLocalCerimonia().getNome() : null;
        EnumStatusEvento status = evento.getStatus();

        // Obter o tipo através da anotaç��o @DiscriminatorValue
        String tipoEvento = evento.getClass().getSimpleName().toString();

        // Campos específicos (default null)
        String instituicao = null;
        String curso = null;
        Integer anoFormatura = null;
        String grauAcademico = null;
        Integer numeroFormandos = null;
        String paraninfo = null;
        String orador = null;
        Boolean temCerimonialista = null;

        if (evento instanceof EventoFormatura ef) {
            instituicao = ef.getInstituicao();
            curso = ef.getCurso();
            anoFormatura = ef.getAnoFormatura();
            grauAcademico = ef.getGrauAcademico();
            numeroFormandos = ef.getNumeroFormandos();
            paraninfo = ef.getParaninfo();
            orador = ef.getOrador();
            temCerimonialista = ef.isTemCerimonialista();
        }
        // else if (evento instanceof OutroTipoEvento ote) { ... }

        return new EventoResponseDto(id, nome, data, capacidadeMaxima, inscritos, organizadorId, tipoEvento, status, instituicao, curso,
                anoFormatura, grauAcademico, numeroFormandos, paraninfo, orador, temCerimonialista, localCerimonia);
    }

}

