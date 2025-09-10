package com.eventos.senac.apieventos_senac.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

//@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Inscricao {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne
    private Usuario usuario;

    // @ManyToOne
    private Evento evento;

    private LocalDateTime dataInscricao;

    private String observacao;

    private String statusPresenca; // Ex: "Confirmado", "Pendente", "Cancelado"

    private String tipoIngresso; // Ex: "VIP", "Normal", etc.

    public Inscricao() {
        this.dataInscricao = LocalDateTime.now();
        this.statusPresenca = "Pendente"; // Padrão ao criar uma inscrição
    }

    public Inscricao(Long id,
                     Usuario usuario,
                     Evento evento,
                     LocalDateTime dataInscricao,
                     String observacao,
                     String statusPresenca,
                     String tipoIngresso) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
        this.dataInscricao = dataInscricao != null ? dataInscricao : LocalDateTime.now();
        this.observacao = observacao;
        this.statusPresenca = statusPresenca != null ? statusPresenca : "Pendente";
        this.tipoIngresso = tipoIngresso;
    }

    public boolean isConfirmed() {
        return "Confirmado".equalsIgnoreCase(statusPresenca);
    }

    public boolean isPending() {
        return "Pendente".equalsIgnoreCase(statusPresenca);
    }

    public boolean isCancelled() {
        return "Cancelado".equalsIgnoreCase(statusPresenca);
    }

}
