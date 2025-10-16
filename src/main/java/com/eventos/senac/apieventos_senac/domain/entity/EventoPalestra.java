package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoPalestraRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private String biografiaPalestrante;

    @Column
    private int tempoPerguntas;

    @Column
    private boolean certificado;

    @Column
    private String objetivosAprendizagem;

    @Column
    private boolean gratuita;

    public EventoPalestra() {
    }

    public EventoPalestra(EventoRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDateTime.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            dto.capacidadeMaxima(), organizador, 0, dto.duracaoMinutos(), dto.precoIngresso(), localCerimonia);

        // Define os campos específicos da palestra
        this.palestrante = dto.palestrante();
        this.tituloPalestra = dto.tituloPalestra();
        this.tema = dto.tema();
        this.categoria = dto.categoria();
        this.biografiaPalestrante = dto.biografiaPalestrante();
        this.tempoPerguntas = dto.tempoPerguntas();
        this.certificado = dto.certificado();
        this.objetivosAprendizagem = dto.objetivosAprendizagem();
        this.gratuita = dto.gratuita();
    }

    public EventoPalestra atualizarEventoFromDTO(EventoPalestra eventoBanco, EventoPalestra eventoDto) {
        eventoBanco.setNome(eventoDto.getNome());
        eventoBanco.setData(eventoDto.getData());
        eventoBanco.setCapacidadeMaxima(eventoDto.getCapacidadeMaxima());
        eventoBanco.setOrganizador(eventoDto.getOrganizador());
        eventoBanco.setDuracaoMinutos(eventoDto.getDuracaoMinutos());
        eventoBanco.setPrecoIngresso(eventoDto.getPrecoIngresso());
        eventoBanco.setLocalCerimonia(eventoDto.getLocalCerimonia());

        // Atualiza os campos específicos da palestra
        eventoBanco.setPalestrante(eventoDto.getPalestrante());
        eventoBanco.setTituloPalestra(eventoDto.getTituloPalestra());
        eventoBanco.setTema(eventoDto.getTema());
        eventoBanco.setCategoria(eventoDto.getCategoria());
        eventoBanco.setBiografiaPalestrante(eventoDto.getBiografiaPalestrante());
        eventoBanco.setTempoPerguntas(eventoDto.getTempoPerguntas());
        eventoBanco.setCertificado(eventoDto.isCertificado());
        eventoBanco.setObjetivosAprendizagem(eventoDto.getObjetivosAprendizagem());
        eventoBanco.setGratuita(eventoDto.isGratuita());
        return eventoBanco;
    }

}
