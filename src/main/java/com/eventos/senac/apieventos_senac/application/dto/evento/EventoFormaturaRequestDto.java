package com.eventos.senac.apieventos_senac.application.dto.evento;

import java.math.BigDecimal;

public record EventoFormaturaRequestDto(
    //atributos comuns da classe pai
    //int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long localCerimoniaId,
    int duracaoMinutos,
    BigDecimal precoIngresso,

    //atributos especificos da classe filha (Formatura)
    String instituicao,
    String curso,
    int anoFormatura,
    String grauAcademico,
    int numeroFormandos,
    String paraninfo,
    String orador,
    boolean temCerimonialista) {

}
