package com.eventos.senac.apieventos_senac.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventoWorkshop extends Evento{
    private String instrutor;
    private String tema;
    private String categoria; // "Tecnologia", "Arte", "Culinária", "Negócios", etc.
    private int duracaoMinutos;
    private BigDecimal precoInscricao;
    private String preRequisitos;
    private boolean certificado;

    public EventoWorkshop() {
    }

    public EventoWorkshop(Long id, String nome, LocalDateTime data, int capacidadeMaxima, Usuario organizador,
                          int inscritos, String instrutor, String tema, String categoria, int duracaoMinutos,
                          BigDecimal precoInscricao, String preRequisitos, boolean certificado) {
        super(id, nome, data, capacidadeMaxima, organizador, inscritos);
        this.instrutor = instrutor;
        this.tema = tema;
        this.categoria = categoria;
        this.duracaoMinutos = duracaoMinutos;
        this.precoInscricao = precoInscricao;
        this.preRequisitos = preRequisitos;
        this.certificado = certificado;
    }
}
