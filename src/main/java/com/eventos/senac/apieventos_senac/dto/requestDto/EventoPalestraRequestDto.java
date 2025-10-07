package com.eventos.senac.apieventos_senac.dto.requestDto;

public record EventoPalestraRequestDto(
    //atributos comuns da classe pai
    int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long organizadorId,
    Long localCerimoniaId,

    //atributos especificos da classe filha (Palestra)
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

}
