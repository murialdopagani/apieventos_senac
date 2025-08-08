package com.eventos.senac.apieventos_senac.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoFormatura extends Evento {
    private String instituicao;
    private String curso;
    private int anoFormatura;
    private String grauAcademico; // "Graduação", "Pós-graduação", "Mestrado", "Doutorado"
    private int numeroFormandos;
    private String paraninfo;
    private String orador;
    private boolean temCerimonialista;
    private String localCerimonia;

    public EventoFormatura() {

    }

    public EventoFormatura(Long id, String nome, LocalDateTime data, int capacidadeMaxima, int inscritos, Usuario organizador,
                           String instituicao, String curso, int anoFormatura, String grauAcademico) {
        super(id, nome, data, capacidadeMaxima, organizador, inscritos);
        this.instituicao = instituicao;
        this.curso = curso;
        this.anoFormatura = anoFormatura;
        this.grauAcademico = grauAcademico;
    }

}
