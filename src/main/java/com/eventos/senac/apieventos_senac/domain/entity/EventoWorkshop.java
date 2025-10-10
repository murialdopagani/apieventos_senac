package com.eventos.senac.apieventos_senac.domain.entity;

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



}
