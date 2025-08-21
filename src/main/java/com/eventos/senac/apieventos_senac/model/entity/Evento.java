package com.eventos.senac.apieventos_senac.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Evento {

    private Long id;

    private String nome;

    private LocalDateTime data;

    private int capacidadeMaxima;

    private int inscritos = 0;

    private Usuario organizador;

    public Evento() {
    }

    public Evento(Long id, String nome, LocalDateTime data, int capacidadeMaxima, Usuario organizador, int inscritos) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.capacidadeMaxima = capacidadeMaxima;
        this.organizador = organizador;
        this.inscritos = inscritos;
    }

    public boolean isFull() {
        return inscritos >= capacidadeMaxima;
    }

    public boolean isFuture() {
        return data.isAfter(LocalDateTime.now());
    }

    public boolean isPast() {
        return data.isBefore(LocalDateTime.now());
    }

}
