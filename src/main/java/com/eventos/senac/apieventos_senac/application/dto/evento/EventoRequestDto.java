package com.eventos.senac.apieventos_senac.application.dto.evento;

import java.math.BigDecimal;

public record EventoRequestDto(
    int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long organizadorId,
    Long localCerimoniaId,
    int duracaoMinutos,
    BigDecimal precoIngresso,

    //campos específicos para Formatura
    String instituicao,
    String curso,
    int anoFormatura,
    String grauAcademico,
    int numeroFormandos,
    String paraninfo,
    String orador,
    boolean temCerimonialista,

    // campos específicos para Palestra
    String palestrante,
    String tituloPalestra,
    String tema,
    String categoria, // "Tecnologia", "Saúde", "Educação", "Negócios", etc.
    String biografiaPalestrante,
    int tempoPerguntas,
    boolean certificado,
    String objetivosAprendizagem,
    boolean gratuita,

    //Campos específicos para Show
    String artista,
    String generoMusical,
    int idadeMinima,
    BigDecimal cacheArtista

) {

    public static EventoRequestDto fromFormaturaDto(EventoFormaturaRequestDto dto) {
        return new EventoRequestDto(
            //dto.tipoEvento(),
            1, //tipoEvento fixo para Formatura
            dto.nome(),
            dto.data(),
            dto.capacidadeMaxima(),
            dto.organizadorId(),
            dto.localCerimoniaId(),
            dto.duracaoMinutos(),
            dto.precoIngresso(),
            dto.instituicao(),
            dto.curso(),
            dto.anoFormatura(),
            dto.grauAcademico(),
            dto.numeroFormandos(),
            dto.paraninfo(),
            dto.orador(),
            dto.temCerimonialista(),
            null, //palestrante
            null, //tituloPalestra
            null, //tema
            null, //categoria
            null, //biografiaPalestrante
            0,    //tempoPerguntas
            false, //certificado
            null, //objetivosAprendizagem
            false, //gratuita
            null, //artista
            null, //generoMusical
            0,    //idadeMinima
            null  //cacheArtista
        );
    }

    public static EventoRequestDto fromPalestraDto(EventoPalestraRequestDto dto) {
        return new EventoRequestDto(
            //dto.tipoEvento(),
            2, //tipoEvento fixo para Palestra
            dto.nome(),
            dto.data(),
            dto.capacidadeMaxima(),
            dto.organizadorId(),
            dto.localCerimoniaId(),
            dto.duracaoMinutos(),
            dto.precoIngresso(),
            null, //instituicao
            null, //curso
            0,    //anoFormatura
            null, //grauAcademico
            0,    //numeroFormandos
            null, //paraninfo
            null, //orador
            false, //temCerimonialista
            dto.palestrante(),
            dto.tituloPalestra(),
            dto.tema(),
            dto.categoria(),
            dto.biografiaPalestrante(),
            dto.tempoPerguntas(),
            dto.certificado(),
            dto.objetivosAprendizagem(),
            dto.gratuita(),
            null, //artista
            null, //generoMusical
            0,    //idadeMinima
            null  //cacheArtista
        );
    }

    public static EventoRequestDto fromShowDto(EventoShowRequestDto dto) {
        return new EventoRequestDto(
            //dto.tipoEvento(),
            3, //tipoEvento fixo para Palestra
            dto.nome(),
            dto.data(),
            dto.capacidadeMaxima(),
            dto.organizadorId(),
            dto.localCerimoniaId(),
            dto.duracaoMinutos(),
            dto.precoIngresso(),
            null, //instituicao
            null, //curso
            0,    //anoFormatura
            null, //grauAcademico
            0,    //numeroFormandos
            null, //paraninfo
            null, //orador
            false, //temCerimonialista
            null, //palestrante
            null, //tituloPalestra
            null, //tema
            null, //categoria
            null, //biografiaPalestrante
            0,    //tempoPerguntas
            false, //certificado
            null, //objetivosAprendizagem
            false, //gratuita
            dto.artista(),
            dto.generoMusical(),
            dto.idadeMinima(),
            dto.cacheArtista()
        );
    }

}

