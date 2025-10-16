package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Data
//@EqualsAndHashCode(callSuper = true)
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

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getAnoFormatura() {
        return anoFormatura;
    }

    public void setAnoFormatura(Integer anoFormatura) {
        this.anoFormatura = anoFormatura;
    }

    public String getGrauAcademico() {
        return grauAcademico;
    }

    public void setGrauAcademico(String grauAcademico) {
        this.grauAcademico = grauAcademico;
    }

    public int getNumeroFormandos() {
        return numeroFormandos;
    }

    public void setNumeroFormandos(int numeroFormandos) {
        this.numeroFormandos = numeroFormandos;
    }

    public String getParaninfo() {
        return paraninfo;
    }

    public void setParaninfo(String paraninfo) {
        this.paraninfo = paraninfo;
    }

    public String getOrador() {
        return orador;
    }

    public void setOrador(String orador) {
        this.orador = orador;
    }

    public boolean isTemCerimonialista() {
        return temCerimonialista;
    }

    public void setTemCerimonialista(boolean temCerimonialista) {
        this.temCerimonialista = temCerimonialista;
    }
}
