package com.eventos.senac.apieventos_senac.application.dto.evento;

import java.math.BigDecimal;

public record EventoWorkshopRequestDto(
    //atributos comuns da classe pai
    //int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long localCerimoniaId,
    int duracaoMinutos,
    BigDecimal precoIngresso,

    //atributos especificos da classe filha (Workshop)
    String palestrante,
    String tema,
    String categoria, // "Tecnologia", "Arte", "Culinária", "Negócios", etc.
    boolean certificado
) {

}
