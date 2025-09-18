package com.eventos.senac.apieventos_senac.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventoShow extends Evento {

    private String artista;

    private String generoMusical;

    private int duracaoShow; // em minutos

    private BigDecimal precoIngresso;

    private int idadeMinima;

    private BigDecimal cacheArtista;

    public EventoShow() {
        super();
    }

    public EventoShow(Long id,
                      String nome,
                      LocalDateTime data,
                      int capacidadeMaxima,
                      Usuario organizador,
                      int inscritos,
                      LocalCerimonia localCerimonia,
                      String artista,
                      String generoMusical,
                      int duracaoShow,
                      BigDecimal precoIngresso,
                      int idadeMinima,
                      BigDecimal cacheArtista) {
        super(id, nome, data, capacidadeMaxima, organizador, inscritos, localCerimonia);
        this.artista = artista;
        this.generoMusical = generoMusical;
        this.duracaoShow = duracaoShow;
        this.precoIngresso = precoIngresso;
        this.idadeMinima = idadeMinima;
        this.cacheArtista = cacheArtista;
    }

}
