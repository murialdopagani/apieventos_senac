package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import jakarta.persistence.*;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.FutureOrPresent;

@Entity
@Table(name = "tb_eventos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoEventos", discriminatorType = DiscriminatorType.INTEGER)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Evento {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long version;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @FutureOrPresent(message = "Data do evento deve ser hoje ou no futuro")
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

    // Mapeamento bidirecional para inscrições do evento
    @OneToMany(mappedBy = "evento", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Inscricao> inscricoes = new ArrayList<>();

    // Helper para manter ambos os lados do relacionamento
    public void addInscricao(Inscricao inscricao) {
        if (inscricao == null) return;
        if (!this.inscricoes.contains(inscricao)) {
            this.inscricoes.add(inscricao);
            inscricao.setEvento(this);
            this.inscritos = this.inscricoes.size();
        }
    }

    public void removeInscricao(Inscricao inscricao) {
        if (inscricao == null) return;
        if (this.inscricoes.remove(inscricao)) {
            inscricao.setEvento(null);
            this.inscritos = this.inscricoes.size();
        }
    }

    // Regra de negócio: cria evento garantindo que a data não seja no passado
    public Evento(Long id,
                  String nome,
                  LocalDateTime data,
                  int capacidadeMaxima,
                  int inscritos,
                  int duracaoMinutos,
                  BigDecimal precoIngresso,
                  Usuario organizador,
                  LocalCerimonia localCerimonia) {
        if (data != null && data.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data do evento não pode ser no passado");
        }
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

    // Regra de negócio: inscrever participante
    public void inscrever(Inscricao inscricao) {
        if (inscricao == null) throw new IllegalArgumentException("Inscrição não pode ser nula");
        if (this.isPast()) {
            throw new IllegalStateException("Não é possível inscrever: evento com data no passado");
        }
        if (this.isFull()) {
            throw new IllegalStateException("Não é possível inscrever: capacidade máxima atingida");
        }
        // evita duplicatas: verifica por usuario.id quando disponível
        final Long novoUsuarioId = inscricao.getUsuario() != null ? inscricao.getUsuario().getId() : null;

        boolean exists = this.inscricoes.stream().anyMatch(i -> {
            Long existingUserId = i.getUsuario() != null ? i.getUsuario().getId() : null;
            if (existingUserId == null || novoUsuarioId == null) {
                return i.equals(inscricao); // fallback para equals
            }
            return novoUsuarioId.equals(existingUserId);
        });

        if (exists) {
            throw new IllegalStateException("Inscrição já existe para este participante neste evento");
        }
        // marca data da inscrição se disponível
        inscricao.setDataInscricao(LocalDateTime.now());
        this.addInscricao(inscricao);
    }

    @PrePersist
    @PreUpdate
    private void validateDateNotPast() {
        if (this.data != null && this.data.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Data do evento não pode ser no passado");
        }
    }

    public boolean isFull() {
        return inscritos >= capacidadeMaxima;
    }

    public boolean isFuture() {
        return data != null && data.isAfter(LocalDateTime.now());
    }

    public boolean isPast() {
        return data != null && data.isBefore(LocalDateTime.now());
    }

    public String getDataFormatada() {
        return data != null ? data.format(FORMATTER) : null;
    }

}
