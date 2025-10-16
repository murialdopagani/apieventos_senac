package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("1")
public class EventoFormatura extends Evento {

    @Column
    private String instituicao;

    @Column
    private String curso;

    @Column
    private Integer anoFormatura;

    @Column
    private String grauAcademico; // "Graduação", "Pós-graduação", "Mestrado", "Doutorado"

    @Column
    private int numeroFormandos;

    @Column
    private String paraninfo;

    @Column
    private String orador;

    @Column
    private boolean temCerimonialista = true;


    public EventoFormatura(EventoRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDateTime.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            dto.capacidadeMaxima(), 0, dto.duracaoMinutos(), dto.precoIngresso(), organizador, localCerimonia);

        // Define os campos específicos da formatura
        this.instituicao = dto.instituicao();
        this.curso = dto.curso();
        this.anoFormatura = dto.anoFormatura();
        this.grauAcademico = dto.grauAcademico();
        this.numeroFormandos = dto.numeroFormandos();
        this.paraninfo = dto.paraninfo();
        this.orador = dto.orador();
        this.temCerimonialista = dto.temCerimonialista();
    }

    public EventoFormatura atualizarEventoFromDTO(EventoFormatura eventoBanco, EventoFormatura eventoDto) {

        eventoBanco.setNome(eventoDto.getNome());
        eventoBanco.setData(eventoDto.getData());
        eventoBanco.setCapacidadeMaxima(eventoDto.getCapacidadeMaxima());
        eventoBanco.setOrganizador(eventoDto.getOrganizador());
        eventoBanco.setDuracaoMinutos(eventoDto.getDuracaoMinutos());
        eventoBanco.setPrecoIngresso(eventoDto.getPrecoIngresso());
        eventoBanco.setLocalCerimonia(eventoDto.getLocalCerimonia());
        eventoBanco.setInstituicao(eventoDto.getInstituicao());
        eventoBanco.setCurso(eventoDto.getCurso());
        eventoBanco.setAnoFormatura(eventoDto.getAnoFormatura());
        eventoBanco.setGrauAcademico(eventoDto.getGrauAcademico());
        eventoBanco.setNumeroFormandos(eventoDto.getNumeroFormandos());
        eventoBanco.setParaninfo(eventoDto.getParaninfo());
        eventoBanco.setOrador(eventoDto.getOrador());
        eventoBanco.setTemCerimonialista(eventoDto.isTemCerimonialista());

        return eventoBanco;
    }

}
