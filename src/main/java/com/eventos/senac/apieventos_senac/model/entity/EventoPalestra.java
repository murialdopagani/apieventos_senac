package com.eventos.senac.apieventos_senac.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventoPalestra extends Evento {

    private String palestrante;

    private String tituloPalestra;

    private String tema;

    private String categoria; // "Tecnologia", "Saúde", "Educação", "Negócios", etc.

    private int duracaoMinutos;

    private String biografiaPalestrante;

    private int tempoPerguntas;

    private boolean certificado;

    private String objetivosAprendizagem;

    private boolean gratuita;

    private BigDecimal precoInscricao;

    public EventoPalestra() {

    }

    public EventoPalestra(Long id, String nome, LocalDateTime data, int capacidadeMaxima, Usuario organizador,
	    int inscritos, String palestrante, String tituloPalestra, String tema, String categoria, int duracaoMinutos,
	    String biografiaPalestrante, int tempoPerguntas, boolean certificado, String objetivosAprendizagem,
	    boolean gratuita, BigDecimal precoInscricao) {
	super(id, nome, data, capacidadeMaxima, organizador, inscritos);
	this.palestrante = palestrante;
	this.tituloPalestra = tituloPalestra;
	this.tema = tema;
	this.categoria = categoria;
	this.duracaoMinutos = duracaoMinutos;
	this.biografiaPalestrante = biografiaPalestrante;
	this.tempoPerguntas = tempoPerguntas;
	this.certificado = certificado;
	this.objetivosAprendizagem = objetivosAprendizagem;
	this.gratuita = gratuita;
	this.precoInscricao = precoInscricao;
    }

}
