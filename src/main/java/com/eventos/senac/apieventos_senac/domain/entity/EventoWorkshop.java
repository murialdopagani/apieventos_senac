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
@DiscriminatorValue("4")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EventoWorkshop extends Evento {

    @Column
    private String palestrante;

    @Column
    private String tema;

    @Column
    private String categoria; // "Tecnologia", "Arte", "Culinária", "Negócios", etc.

    @Column
    private boolean certificado;

    public EventoWorkshop(EventoRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDateTime.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            dto.capacidadeMaxima(), 0,0, dto.duracaoMinutos(), dto.precoIngresso(), organizador, localCerimonia);

        // Define os campos específicos do EventoWorkshop
        this.palestrante = dto.palestrante();
        this.tema = dto.tema();
        this.categoria = dto.categoria();
        this.certificado = dto.certificado();
    }

    public EventoWorkshop atualizarEventoFromDTO(EventoWorkshop eventoBanco, EventoWorkshop eventoDto) {
        eventoBanco.setNome(eventoDto.getNome());
        eventoBanco.setData(eventoDto.getData());
        eventoBanco.setCapacidadeMaxima(eventoDto.getCapacidadeMaxima());
        eventoBanco.setOrganizador(eventoDto.getOrganizador());
        eventoBanco.setDuracaoMinutos(eventoDto.getDuracaoMinutos());
        eventoBanco.setPrecoIngresso(eventoDto.getPrecoIngresso());
        eventoBanco.setLocalCerimonia(eventoDto.getLocalCerimonia());

        // Atualiza os campos específicos do EventoShow
        eventoBanco.setPalestrante(eventoDto.getPalestrante());
        eventoBanco.setTema(eventoDto.getTema());
        eventoBanco.setCategoria(eventoDto.getCategoria());
        eventoBanco.setCertificado(eventoDto.isCertificado());
        return eventoBanco;
    }

}
