package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoWorkshopRequestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("4")
public class EventoWorkshop extends Evento {

    private String instrutor;

    private String tema;

    private String categoria; // "Tecnologia", "Arte", "Culinária", "Negócios", etc.

    private boolean certificado;

    public EventoWorkshop() {
        super();
    }

    public EventoWorkshop(EventoWorkshopRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {
        // Chama o construtor da classe pai que já existe
        super(null, dto.nome(), LocalDateTime.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            dto.capacidadeMaxima(), organizador, 0, dto.duracaoMinutos(), dto.precoIngresso(), localCerimonia);

        // Define os campos específicos do EventoWorkshop
        this.instrutor = dto.instrutor();
        this.tema = dto.tema();
        this.categoria = dto.categoria();
        this.certificado = dto.certificado();
    }

    public EventoWorkshop atualizarEventoFromDTO(EventoWorkshop eventoBanco, EventoWorkshopRequestDto dto, Usuario organizador,
        LocalCerimonia localCerimonia) {
        eventoBanco.setNome(dto.nome());
        eventoBanco.setData(LocalDateTime.parse(dto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        eventoBanco.setCapacidadeMaxima(dto.capacidadeMaxima());
        eventoBanco.setOrganizador(organizador);
        eventoBanco.setDuracaoMinutos(dto.duracaoMinutos());
        eventoBanco.setPrecoIngresso(dto.precoIngresso());
        eventoBanco.setLocalCerimonia(localCerimonia);

        // Atualiza os campos específicos do EventoShow
        eventoBanco.setInstrutor(dto.instrutor());
        eventoBanco.setTema(dto.tema());
        eventoBanco.setCategoria(dto.categoria());
        eventoBanco.setCertificado(dto.certificado());
        return eventoBanco;
    }

}
