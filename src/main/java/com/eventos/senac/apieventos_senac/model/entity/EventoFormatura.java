package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
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

    public EventoFormatura() {

    }

    public EventoFormatura(EventoCriarRequestDto dto,
                           Usuario organizador,
                           LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                         .atStartOfDay(), dto.capacidadeMaxima(), organizador, 0, localCerimonia);

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

    public EventoFormatura atualizarEventoFromDTO(EventoFormatura eventoBanco,
                                                  EventoCriarRequestDto dto,
                                                  Usuario organizador,
                                                  LocalCerimonia localCerimonia) {
        // Atualiza os campos da classe pai
        super.setNome(dto.nome());
        super.setData(LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                               .atStartOfDay());
        super.setCapacidadeMaxima(dto.capacidadeMaxima());
        // O campo 'inscritos' não deve ser atualizado aqui, pois é gerenciado internamente

        // Atualiza os campos específicos da formatura
        eventoBanco.setOrganizador(organizador);
        eventoBanco.setLocalCerimonia(localCerimonia);
        eventoBanco.setInstituicao(dto.instituicao());
        eventoBanco.setCurso(dto.curso());
        eventoBanco.setAnoFormatura(dto.anoFormatura());
        eventoBanco.setGrauAcademico(dto.grauAcademico());
        eventoBanco.setNumeroFormandos(dto.numeroFormandos());
        eventoBanco.setParaninfo(dto.paraninfo());
        eventoBanco.setOrador(dto.orador());
        eventoBanco.setTemCerimonialista(dto.temCerimonialista());

        return this;
    }
}
