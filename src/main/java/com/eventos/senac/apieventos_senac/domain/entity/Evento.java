package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tb_eventos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoEventos", discriminatorType = DiscriminatorType.INTEGER)
public class Evento {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private int capacidadeMaxima;

    @Column(nullable = false)
    private int inscritos = 0;

    @Column
    private int duracaoMinutos;

    @Column
    private BigDecimal precoIngresso = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_organizador"))
    private Usuario organizador;

    @ManyToOne
    @JoinColumn(name = "local_cerimonia_id", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_localCerimonia"))
    private LocalCerimonia localCerimonia;


    @Column
    private EnumStatusEvento status = EnumStatusEvento.ATIVO;

    public Evento() {
    }

    public Evento(Long id,
                  String nome,
                  LocalDateTime data,
                  int capacidadeMaxima,
                  Usuario organizador,
                  int inscritos,
                  int duracaoMinutos,
                  BigDecimal precoIngresso,
                  LocalCerimonia localCerimonia) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.capacidadeMaxima = capacidadeMaxima;
        this.organizador = organizador;
        this.inscritos = inscritos;
        this.duracaoMinutos = duracaoMinutos;
        this.precoIngresso = precoIngresso;
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

    public String getDataFormatada() {
        return data.format(FORMATTER);
    }

}
