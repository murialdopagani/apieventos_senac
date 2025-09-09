package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.dto.EventoFormaturaCriarRequestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_eventos_formatura")
@DiscriminatorValue("FORMATURA")
@PrimaryKeyJoinColumn(name = "evento_id", referencedColumnName = "id")
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

    public EventoFormatura(EventoFormaturaCriarRequestDto dto, Usuario organizador) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay(),
                dto.capacidadeMaxima(), organizador, 0);

        // Define os campos específicos da formatura
        this.instituicao = dto.instituicao();
        this.curso = dto.curso();
        this.anoFormatura = dto.anoFormatura();
        this.grauAcademico = dto.grauAcademico();
        this.numeroFormandos = dto.numeroFormandos();
        this.paraninfo = dto.paraninfo();
        this.orador = dto.orador();
        this.temCerimonialista = dto.temCerimonialista();
        this.localCerimonia = dto.localCerimonia();
    }

}
