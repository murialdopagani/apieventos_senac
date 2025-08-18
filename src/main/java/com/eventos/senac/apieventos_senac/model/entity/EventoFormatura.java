package com.eventos.senac.apieventos_senac.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

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

    public EventoFormatura(Long id, String nome, LocalDateTime data, int capacidadeMaxima, Usuario organizador,
	    int inscritos, String instituicao, String curso, int anoFormatura, String grauAcademico,
	    int numeroFormandos, String paraninfo, String orador, boolean temCerimonialista, String localCerimonia) {
	super(id, nome, data, capacidadeMaxima, organizador, inscritos);
	this.instituicao = instituicao;
	this.curso = curso;
	this.anoFormatura = anoFormatura;
	this.grauAcademico = grauAcademico;
	this.numeroFormandos = numeroFormandos;
	this.paraninfo = paraninfo;
	this.orador = orador;
	this.temCerimonialista = temCerimonialista;
	this.localCerimonia = localCerimonia;
    }
}
