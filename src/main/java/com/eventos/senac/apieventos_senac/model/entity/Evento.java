package com.eventos.senac.apieventos_senac.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_evento")
@SequenceGenerator(name = "seq_evento", sequenceName = "seq_evento", allocationSize = 1, initialValue = 1)
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evento")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private int capacidadeMaxima;

    @Column(nullable = false)
    private int inscritos = 0;

    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_organizador"))
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

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public Evento(EventoCriarRequestDto eventoCriarRequestDto, Usuario organizador) {
        LocalDate dataEvento = LocalDate.parse(eventoCriarRequestDto.data(), FORMATTER);

        this.nome = eventoCriarRequestDto.nome();
        this.data = dataEvento.atStartOfDay(); // Define como início do dia (00:00:00)
        this.capacidadeMaxima = eventoCriarRequestDto.capacidadeMaxima();
        this.organizador = organizador;
        this.inscritos = 0;
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
