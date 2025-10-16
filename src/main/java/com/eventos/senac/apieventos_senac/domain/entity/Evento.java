package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import jakarta.persistence.*;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Data
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
                  int inscritos,
                  int duracaoMinutos,
                  BigDecimal precoIngresso,
                  Usuario organizador,
                  LocalCerimonia localCerimonia) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.capacidadeMaxima = capacidadeMaxima;
        this.inscritos = inscritos;
        this.duracaoMinutos = duracaoMinutos;
        this.precoIngresso = precoIngresso;
        this.organizador = organizador;
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

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public BigDecimal getPrecoIngresso() {
        return precoIngresso;
    }

    public void setPrecoIngresso(BigDecimal precoIngresso) {
        this.precoIngresso = precoIngresso;
    }

    public Usuario getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Usuario organizador) {
        this.organizador = organizador;
    }

    public LocalCerimonia getLocalCerimonia() {
        return localCerimonia;
    }

    public void setLocalCerimonia(LocalCerimonia localCerimonia) {
        this.localCerimonia = localCerimonia;
    }

    public EnumStatusEvento getStatus() {
        return status;
    }

    public void setStatus(EnumStatusEvento status) {
        this.status = status;
    }
}
