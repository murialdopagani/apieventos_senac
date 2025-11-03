package com.eventos.senac.apieventos_senac.application.dto.evento;

import java.math.BigDecimal;

public record EventoShowRequestDto(
    //atributos comuns da classe pai
    //int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long localCerimoniaId,
    int duracaoMinutos,
    BigDecimal precoIngresso,

    //atributos especificos da classe filha (Show)
    String artista,
    String generoMusical,
    int idadeMinima,
    BigDecimal cacheArtista
) {

}
