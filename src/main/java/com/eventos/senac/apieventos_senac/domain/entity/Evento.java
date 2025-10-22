package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.FutureOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_eventos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoEventos", discriminatorType = DiscriminatorType.INTEGER)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class Evento {

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
    private int inscritosConfirmado = 0;

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


    // Regra de negócio: cria evento garantindo que a data não seja no passado
    public Evento(Long id,
        String nome,
        LocalDateTime data,
        int capacidadeMaxima,
        int inscritos,
        int inscritosConfirmado,
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
        this.inscritosConfirmado = inscritosConfirmado;
        this.duracaoMinutos = duracaoMinutos;
        this.precoIngresso = precoIngresso;
        this.organizador = organizador;
        this.localCerimonia = localCerimonia;
    }


    // Regra de negócio: inscrever participantes
    public void inscrever(Inscricao inscricao) {
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não pode ser nula");
        }
        if (this.isPast()) {
            throw new IllegalStateException("Não é possível inscrever: evento com data no passado");
        }
        if (this.isFull()) {
            throw new IllegalStateException("Não é possível inscrever: capacidade máxima atingida");
        }
        // evita duplicatas: verifica por usuario.id quando disponível
        final Long novoUsuarioId = inscricao.getUsuario() != null ? inscricao.getUsuario().getId() : null;

        boolean existe;
        if (novoUsuarioId == null) {
            // Se a nova inscrição não tem usuário, a única opção é comparar pelo objeto.
            existe = this.inscricoes.stream().anyMatch(existente -> existente.equals(inscricao));
        } else {
            // Se a nova inscrição tem usuário, a prioridade é verificar pelo ID do usuário.
            existe = this.inscricoes.stream()
                .map(Inscricao::getUsuario)         // Pega o usuário de cada inscrição existente
                .filter(Objects::nonNull)           // Descarta as que não têm usuário
                .map(Usuario::getId)                // Pega o ID do usuário
                .anyMatch(id -> id != null && id.equals(novoUsuarioId)); // Compara com o novo ID
        }

        if (existe) {
            throw new IllegalStateException("Inscrição já existe para este participante neste evento");
        }

        inscricao.setDataInscricao(LocalDateTime.now());
        this.addInscricao(inscricao);
    }

    public void addInscricao(Inscricao inscricao) {
        if (inscricao == null) {
            return;
        }
        if (!this.inscricoes.contains(inscricao)) {
            this.inscricoes.add(inscricao);
            inscricao.setEvento(this);
            this.inscritos = this.inscricoes.size();
        }
    }


    // Remove inscrição e ajusta os contadores.
    public void removeInscricao(Inscricao inscricao) {
        if (inscricao == null) {
            return;
        }

        if (this.inscricoes.remove(inscricao)) {
            inscricao.setEvento(null);
            this.inscritos = this.inscricoes.size();
            if (inscricao.isConfirmada() && this.inscritosConfirmado > 0) {
                this.inscritosConfirmado--;
            }
        }
    }


    /**
     * Confirma presença da inscrição e atualiza o contador de inscritos confirmados.
     */
    public void confirmarPresenca(Inscricao inscricao) {
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscrição não pode ser nula");
        }
        if (!this.inscricoes.contains(inscricao)) {
            throw new IllegalStateException("Inscrição não pertence a este evento");
        }
        if (inscricao.isConfirmada()) {
            return; // já confirmada
        }
        if (isFull()) {
            throw new IllegalStateException("Capacidade máxima atingida para confirmações");
        }
        this.inscritosConfirmado++;
    }


    // Recalcula ambos os contadores a partir da coleção.
    public void recomputeContadores() {
        this.inscritos = this.inscricoes.size();
        this.inscritosConfirmado = (int) this.inscricoes.stream()
            .filter(Inscricao::isConfirmada)
            .count();
    }

    public boolean isFull() {
        return inscritosConfirmado >= capacidadeMaxima;
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
