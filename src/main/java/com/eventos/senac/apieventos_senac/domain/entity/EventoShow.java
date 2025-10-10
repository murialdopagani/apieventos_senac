package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
        super();
    }

    public EventoShow(EventoRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .atStartOfDay(), dto.capacidadeMaxima(), organizador, 0,dto.duracaoMinutos(), dto.precoIngresso(), localCerimonia);

        // Define os campos específicos do EventoShow
        this.artista = dto.artista();
        this.generoMusical = dto.generoMusical();
        this.idadeMinima = dto.idadeMinima();
        this.cacheArtista = dto.cacheArtista();
    }

    public EventoShow atualizarEventoFromDTO(EventoShow eventoBanco, EventoRequestDto dto, Usuario organizador,
        LocalCerimonia localCerimonia) {
        eventoBanco.setNome(dto.nome());
        eventoBanco.setData(LocalDate.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .atStartOfDay());
        eventoBanco.setCapacidadeMaxima(dto.capacidadeMaxima());
        eventoBanco.setOrganizador(organizador);
        eventoBanco.setDuracaoMinutos(dto.duracaoMinutos());
        eventoBanco.setPrecoIngresso(dto.precoIngresso());
        eventoBanco.setLocalCerimonia(localCerimonia);

        // Atualiza os campos específicos do EventoShow
        eventoBanco.setArtista(dto.artista());
        eventoBanco.setGeneroMusical(dto.generoMusical());
        eventoBanco.setIdadeMinima(dto.idadeMinima());
        eventoBanco.setCacheArtista(dto.cacheArtista());
        return eventoBanco;
    }

}
