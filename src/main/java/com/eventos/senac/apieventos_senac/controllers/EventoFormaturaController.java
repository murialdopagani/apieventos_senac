package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.EventoFormaturaCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.EventoFormaturaResponseDto;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.service.EventoFormaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evento_formatura")
@Tag(name = "Evento Controller", description = "Controladora responsável por gerenciar os Eventos")
public class EventoFormaturaController {

    @Autowired
    private EventoFormaturaService eventoFormaturaService;

    @PostMapping(value = "/criar")
    @Operation(summary = "Cria um Evento de Formatura", description = "Método responsável por criar um Evento Formatura no sistema.")
    public ResponseEntity<EventoFormaturaResponseDto> criarEventoFormatura(EventoFormaturaCriarRequestDto eventoFormaturaCriarRequestDto) {
        try {
            EventoFormatura eventoCriado = eventoFormaturaService.criarEventoFormatura(eventoFormaturaCriarRequestDto);
            EventoFormaturaResponseDto eventoResponseDto = EventoFormaturaResponseDto.fromEvento(eventoCriado);

            return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
        } catch (Exception e) {
            System.err.println("Erro ao criar evento formatura: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
