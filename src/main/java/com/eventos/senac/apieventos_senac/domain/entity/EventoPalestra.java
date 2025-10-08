package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.requestDto.EventoCriarRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("2")
public class EventoPalestra extends Evento {

    @Column
    private String palestrante;

    @Column
    private String tituloPalestra;

    @Column
    private String tema;

    @Column
    private String categoria; // "Tecnologia", "Saúde", "Educação", "Negócios", etc.

    @Column
    private int duracaoMinutos;

    @Column
    private String biografiaPalestrante;

    @Column
    private int tempoPerguntas;

    @Column
    private boolean certificado;

    @Column
    private String objetivosAprendizagem;

    @Column
    private boolean gratuita;

    @Column
    private BigDecimal precoInscricao;

    public EventoPalestra() {

    }

    public EventoPalestra(EventoCriarRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia){
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .atStartOfDay(), dto.capacidadeMaxima(), organizador, 0, localCerimonia);

        // Define os campos específicos da palestra
        this.palestrante = dto.palestrante();
        this.tituloPalestra = dto.tituloPalestra();
        this.tema = dto.tema();
        this.categoria = dto.categoria();
        this.duracaoMinutos = dto.duracaoMinutos();
        this.biografiaPalestrante = dto.biografiaPalestrante();
        this.tempoPerguntas = dto.tempoPerguntas();
        this.certificado = dto.certificado();
        this.objetivosAprendizagem = dto.objetivosAprendizagem();
        this.gratuita = dto.gratuita();
        this.precoInscricao = dto.precoInscricao() != null ? new BigDecimal(dto.precoInscricao()) : BigDecimal.ZERO;
    }

    public EventoPalestra atualizarEventoFromDTO(EventoPalestra eventoBanco,
                                                 EventoCriarRequestDto dto,
                                                 Usuario organizador,
                                                 LocalCerimonia localCerimonia) {

        eventoBanco.setNome(dto.nome());
        eventoBanco.setData(LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .atStartOfDay());
        eventoBanco.setCapacidadeMaxima(dto.capacidadeMaxima());
        eventoBanco.setOrganizador(organizador);
        eventoBanco.setLocalCerimonia(localCerimonia);

        // Atualiza os campos específicos da palestra
        eventoBanco.setPalestrante(dto.palestrante());
        eventoBanco.setTituloPalestra(dto.tituloPalestra());
        eventoBanco.setTema(dto.tema());
        eventoBanco.setCategoria(dto.categoria());
        eventoBanco.setDuracaoMinutos(dto.duracaoMinutos());
        eventoBanco.setBiografiaPalestrante(dto.biografiaPalestrante());
        eventoBanco.setTempoPerguntas(dto.tempoPerguntas());
        eventoBanco.setCertificado(dto.certificado());
        eventoBanco.setObjetivosAprendizagem(dto.objetivosAprendizagem());
        eventoBanco.setGratuita(dto.gratuita());
        eventoBanco.setPrecoInscricao(dto.precoInscricao() != null ? new BigDecimal(dto.precoInscricao()) : BigDecimal.ZERO);
        return eventoBanco;
    }

}
