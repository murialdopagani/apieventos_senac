package com.eventos.senac.apieventos_senac.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventoWorkshop extends Evento {

    private String instrutor;

    private String tema;

    private String categoria; // "Tecnologia", "Arte", "Culinária", "Negócios", etc.

    private int duracaoMinutos;

    private BigDecimal precoInscricao;

    private String preRequisitos;

    private boolean certificado;

    public EventoWorkshop() {
        super();
    }

    public EventoWorkshop(Long id,
                          String nome,
                          LocalDateTime data,
                          int capacidadeMaxima,
                          Usuario organizador,
                          int inscritos,
                          LocalCerimonia localCerimonia,
                          String instrutor,
                          String tema,
                          String categoria,
                          int duracaoMinutos,
                          BigDecimal precoInscricao,
                          String preRequisitos,
                          boolean certificado) {
        super(id, nome, data, capacidadeMaxima, organizador, inscritos, localCerimonia);
        this.instrutor = instrutor;
        this.tema = tema;
        this.categoria = categoria;
        this.duracaoMinutos = duracaoMinutos;
        this.precoInscricao = precoInscricao;
        this.preRequisitos = preRequisitos;
        this.certificado = certificado;

    }

}
