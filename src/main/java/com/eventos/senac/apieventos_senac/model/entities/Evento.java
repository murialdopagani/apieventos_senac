package com.eventos.senac.apieventos_senac.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Evento {
    private Long id;
    private String nome;
    private LocalDateTime data;
    private int capacidadeMaxima;
    private Usuario organizador;
    private int inscritos = 0;

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

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data=" + data +
                ", capacidadeMaxima=" + capacidadeMaxima +
                ", inscritos=" + inscritos +
                ", organizador=" + organizador +
                '}';
    }
}
