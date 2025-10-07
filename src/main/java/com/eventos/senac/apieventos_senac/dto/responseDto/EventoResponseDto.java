package com.eventos.senac.apieventos_senac.dto.responseDto;

import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventoResponseDto(Long id, String nome, String data, int capacidadeMaxima, int inscritos, Long organizadorId,
                                String tipoEvento, EnumStatusEvento status,

                                //Campos específicos de formatura
                                String instituicao, String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                String paraninfo, String orador, Boolean temCerimonialista, String localCerimonia,

                                //Campos específicos de palestra
                                String palestrante, String tituloPalestra, String tema, String categoria, int duracaoMinutos,
                                String biografiaPalestrante,
                                int tempoPerguntas, Boolean certificado, String objetivosAprendizagem, Boolean gratuita,
                                BigDecimal precoInscricao

) {

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

        // Campos específicos formatura(default null)
        String instituicao = null;
        String curso = null;
        Integer anoFormatura = 0;
        String grauAcademico = null;
        Integer numeroFormandos = 0;
        String paraninfo = null;
        String orador = null;
        Boolean temCerimonialista = false;

        // Campos específicos palestra(default null)
        String palestrante = null;
        String tituloPalestra = null;
        String tema = null;
        String categoria = null; // "Tecnologia", "Saúde", "Educação", "Negócios", etc.
        Integer duracaoMinutos = 0;
        String biografiaPalestrante = null;
        Integer tempoPerguntas = 0;
        Boolean certificado = true;
        String objetivosAprendizagem = null;
        Boolean gratuita = false;
        BigDecimal precoInscricao = BigDecimal.ZERO;

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

        if (evento instanceof EventoPalestra ep) {
            palestrante = ep.getPalestrante();
            tituloPalestra = ep.getTituloPalestra();
            tema = ep.getTema();
            categoria = ep.getCategoria();
            duracaoMinutos = ep.getDuracaoMinutos();
            biografiaPalestrante = ep.getBiografiaPalestrante();
            tempoPerguntas = ep.getTempoPerguntas();
            certificado = ep.isCertificado();
            objetivosAprendizagem = ep.getObjetivosAprendizagem();
            gratuita = ep.isGratuita();
            precoInscricao = ep.getPrecoInscricao();
        }

        // else if (evento instanceof OutroTipoEvento ote) { ... }

        return new EventoResponseDto(id, nome, data, capacidadeMaxima, inscritos, organizadorId, tipoEvento, status, instituicao,
            curso, anoFormatura, grauAcademico, numeroFormandos, paraninfo, orador, temCerimonialista, localCerimonia, palestrante,
            tituloPalestra, tema, categoria, duracaoMinutos, biografiaPalestrante, tempoPerguntas, certificado,
            objetivosAprendizagem,
            gratuita, precoInscricao);
    }

}

