package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoShowRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
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
@DiscriminatorValue("3")
public class EventoShow extends Evento {

    @Column
    private String artista;

    @Column
    private String generoMusical;

    @Column
    private int idadeMinima;

    @Column
    private BigDecimal cacheArtista = BigDecimal.ZERO;

    public EventoShow() {
    }

    public EventoShow(EventoRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDateTime.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            dto.capacidadeMaxima(), organizador, 0, dto.duracaoMinutos(), dto.precoIngresso(), localCerimonia);

        // Define os campos específicos do EventoShow
        this.artista = dto.artista();
        this.generoMusical = dto.generoMusical();
        this.idadeMinima = dto.idadeMinima();
        this.cacheArtista = dto.cacheArtista();
    }

    public EventoShow atualizarEventoFromDTO(EventoShow eventoBanco, EventoShow eventoDto) {
        eventoBanco.setNome(eventoDto.getNome());
        eventoBanco.setData(eventoDto.getData());
        eventoBanco.setCapacidadeMaxima(eventoDto.getCapacidadeMaxima());
        eventoBanco.setOrganizador(eventoDto.getOrganizador());
        eventoBanco.setDuracaoMinutos(eventoDto.getDuracaoMinutos());
        eventoBanco.setPrecoIngresso(eventoDto.getPrecoIngresso());
        eventoBanco.setLocalCerimonia(eventoDto.getLocalCerimonia());

        // Atualiza os campos específicos do EventoShow
        eventoBanco.setArtista(eventoDto.getArtista());
        eventoBanco.setGeneroMusical(eventoDto.getGeneroMusical());
        eventoBanco.setIdadeMinima(eventoDto.getIdadeMinima());
        eventoBanco.setCacheArtista(eventoDto.getCacheArtista());
        return eventoBanco;
    }

}
