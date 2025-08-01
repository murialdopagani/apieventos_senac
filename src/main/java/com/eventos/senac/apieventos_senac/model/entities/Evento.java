package com.eventos.senac.apieventos_senac.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private LocalDateTime data;
    private Integer capacidadeMaxima;
    private Integer inscritos;
    private Usuario organizador;

    public Evento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Integer getInscritos() {
        return inscritos;
    }

    public void setInscritos(Integer inscritos) {
        this.inscritos = inscritos;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
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
