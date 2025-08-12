package com.eventos.senac.apieventos_senac.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

//@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Inscricao {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    private Usuario usuario;

    //@ManyToOne
    private Evento evento;

    private LocalDateTime dataInscricao;

    private String observacao;

    private String statusPresenca; // Ex: "Confirmado", "Pendente", "Cancelado"

    private String tipoIngresso; // Ex: "VIP", "Normal", etc.

}
