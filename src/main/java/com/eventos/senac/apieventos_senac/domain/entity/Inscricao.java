package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoRequestDto;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusPresenca;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumTipoIngresso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_inscricao", uniqueConstraints = @UniqueConstraint(columnNames = {"evento_id", "usuario_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column
    private LocalDateTime dataInscricao = LocalDateTime.now();

    @Column
    private String codigoPromocional;

    @Column
    private EnumStatusPresenca statusPresenca = EnumStatusPresenca.PENDENTE;

    @Column
    private EnumTipoIngresso tipoIngresso;


    public Inscricao(InscricaoRequestDto requestDto, Usuario usuario, Evento evento) {
        this.usuario = usuario;
        this.evento = evento;
        this.codigoPromocional = requestDto.codigoPromocional();
        this.tipoIngresso = requestDto.tipoIngresso();
    }

    // Verifica se a inscrição está como CONFIRMADO
    public boolean isConfirmed() {
        return EnumStatusPresenca.CONFIRMADO.equals(this.statusPresenca);
    }

    // Verifica se a inscrição está como PENDENTE
    public boolean isPending() {
        return EnumStatusPresenca.PENDENTE.equals(this.statusPresenca);
    }

    // Verifica se a inscrição está como CANCELADO
    public boolean isCancelled() {
        return EnumStatusPresenca.CANCELADO.equals(this.statusPresenca);
    }

    // Método utilitário para marcar a inscrição como confirmada
    public void confirm() {
        this.statusPresenca = EnumStatusPresenca.CONFIRMADO;
    }


}
