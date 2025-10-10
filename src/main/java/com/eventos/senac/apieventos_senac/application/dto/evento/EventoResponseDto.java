package com.eventos.senac.apieventos_senac.application.dto.evento;

import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.domain.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.domain.entity.EventoShow;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventoResponseDto(Long id, String nome, String data, int capacidadeMaxima, int inscritos, String organizador,
                                int duracaoMinutos, BigDecimal precoIngresso, String localCerimonia, EnumStatusEvento status,
                                String tipoEvento,

                                //Campos específicos de formatura
                                String instituicao, String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                String paraninfo, String orador, Boolean temCerimonialista,

                                //Campos específicos de palestra
                                String palestrante, String tituloPalestra, String tema, String categoria,
                                String biografiaPalestrante, int tempoPerguntas, Boolean certificado, String objetivosAprendizagem,
                                Boolean gratuita,

                                //Campos específicos de show
                                String artista, String generoMusical, int idadeMinima, BigDecimal cacheArtista

) {

    public static EventoResponseDto fromEvento(Evento evento) {

        //Valores base(comuns a todos os eventos)
        Long id = evento.getId();
        String nome = evento.getNome();
        String data = evento.getData() != null ? evento.getData().toString() : null;
        int capacidadeMaxima = evento.getCapacidadeMaxima();
        int inscritos = evento.getInscritos();
        String organizador = evento.getOrganizador().getNome();
        Integer duracaoMinutos = evento.getDuracaoMinutos();
        BigDecimal precoIngresso = evento.getPrecoIngresso();
        String localCerimonia = evento.getLocalCerimonia().getNome();
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
        String biografiaPalestrante = null;
        Integer tempoPerguntas = 0;
        Boolean certificado = true;
        String objetivosAprendizagem = null;
        Boolean gratuita = false;

        //Campo específicos show (default null)
        String artista = null;
        String generoMusical = null;
        Integer idadeMinima = 0;
        BigDecimal cacheArtista = BigDecimal.ZERO;

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
            biografiaPalestrante = ep.getBiografiaPalestrante();
            tempoPerguntas = ep.getTempoPerguntas();
            certificado = ep.isCertificado();
            objetivosAprendizagem = ep.getObjetivosAprendizagem();
            gratuita = ep.isGratuita();
        }

        if (evento instanceof EventoShow eventoShow) {
            artista = eventoShow.getArtista();
            generoMusical = eventoShow.getGeneroMusical();
            idadeMinima = eventoShow.getIdadeMinima();
            cacheArtista = eventoShow.getCacheArtista();
        }
        // else if (evento instanceof OutroTipoEvento ote) { ... }

        return new EventoResponseDto(id, nome, data, capacidadeMaxima, inscritos, organizador, duracaoMinutos,
            precoIngresso, localCerimonia, status, tipoEvento, instituicao, curso, anoFormatura,
            grauAcademico, numeroFormandos, paraninfo, orador, temCerimonialista, palestrante,
            tituloPalestra, tema, categoria, biografiaPalestrante, tempoPerguntas, certificado,
            objetivosAprendizagem, gratuita, artista, generoMusical, idadeMinima, cacheArtista);
    }

}

