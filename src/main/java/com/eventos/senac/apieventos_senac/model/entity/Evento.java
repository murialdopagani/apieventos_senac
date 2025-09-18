package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusUsuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "tb_eventos")
@SequenceGenerator(name = "seq_eventos", sequenceName = "seq_eventos", allocationSize = 1, initialValue = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoEventos", discriminatorType = DiscriminatorType.INTEGER)
public class Evento {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_eventos")
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

    @ManyToOne
    @JoinColumn(name = "local_cerimonia_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_localCerimonia"))
    private LocalCerimonia localCerimonia;

    private EnumStatusEvento status = EnumStatusEvento.ATIVO;

    public Evento() {
    }

    public Evento(Long id, String nome, LocalDateTime data, int capacidadeMaxima, Usuario organizador, int inscritos, LocalCerimonia localCerimonia) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.capacidadeMaxima = capacidadeMaxima;
        this.organizador = organizador;
        this.inscritos = inscritos;
        this.localCerimonia = localCerimonia;
    }

//    public Evento(EventoCriarRequestDto eventoCriarRequestDto, Usuario organizador) {
//        LocalDate dataEvento = LocalDate.parse(eventoCriarRequestDto.data(), FORMATTER);
//
//        this.nome = eventoCriarRequestDto.nome();
//        this.data = dataEvento.atStartOfDay(); // Define como início do dia (00:00:00)
//        this.capacidadeMaxima = eventoCriarRequestDto.capacidadeMaxima();
//        this.organizador = organizador;
//        this.inscritos = 0;
//    }

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
