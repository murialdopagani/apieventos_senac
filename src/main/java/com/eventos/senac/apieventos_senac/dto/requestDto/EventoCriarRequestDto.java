package com.eventos.senac.apieventos_senac.dto.requestDto;

public record EventoCriarRequestDto(
    int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long organizadorId,
    Long localCerimoniaId,
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
    int duracaoMinutos,
    String biografiaPalestrante,
    int tempoPerguntas,
    boolean certificado,
    String objetivosAprendizagem,
    boolean gratuita,
    String precoInscricao

) {

    public static EventoCriarRequestDto fromFormaturaDto(EventoFormaturaRequestDto dto) {
        return new EventoCriarRequestDto(
            //dto.tipoEvento(),
            1, //tipoEvento fixo para Formatura
            dto.nome(),
            dto.data(),
            dto.capacidadeMaxima(),
            dto.organizadorId(),
            dto.localCerimoniaId(),
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
            0,    //duracaoMinutos
            null, //biografiaPalestrante
            0,    //tempoPerguntas
            false, //certificado
            null, //objetivosAprendizagem
            false, //gratuita
            null  //precoInscricao
        );
    }

    public static EventoCriarRequestDto fromPalestraDto(EventoPalestraRequestDto dto) {
        return new EventoCriarRequestDto(
            //dto.tipoEvento(),
            2, //tipoEvento fixo para Palestra
            dto.nome(),
            dto.data(),
            dto.capacidadeMaxima(),
            dto.organizadorId(),
            dto.localCerimoniaId(),
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
            dto.duracaoMinutos(),
            dto.biografiaPalestrante(),
            dto.tempoPerguntas(),
            dto.certificado(),
            dto.objetivosAprendizagem(),
            dto.gratuita(),
            dto.precoInscricao()
        );
    }



}

