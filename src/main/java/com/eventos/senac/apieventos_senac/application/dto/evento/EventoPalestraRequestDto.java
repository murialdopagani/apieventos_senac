package com.eventos.senac.apieventos_senac.application.dto.evento;

import java.math.BigDecimal;

public record EventoPalestraRequestDto(
    //atributos comuns da classe pai
    //int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long localCerimoniaId,
    int duracaoMinutos,
    BigDecimal precoIngresso,

    //atributos especificos da classe filha (Palestra)
    String palestrante,
    String tituloPalestra,
    String tema,
    String categoria, // "Tecnologia", "Saúde", "Educação", "Negócios", etc.
    String biografiaPalestrante,
    int tempoPerguntas,
    boolean certificado,
    String objetivosAprendizagem,
    boolean gratuita
    ) {

}
