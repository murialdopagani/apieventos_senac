package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.EventoResponseDto;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evento")
@Tag(name = "Evento Controller", description = "Controladora responsável por gerenciar os Eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @Operation(summary = "Cria/Atualiza um Evento", description = "Método responsável por criar/atualizar um Evento no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoCriarRequestDto eventoCriarRequestDto) {

        try {
            var eventoBanco = eventoService.criarEvento(eventoCriarRequestDto);

            // Verificação de tipo e conversão segura
            if (eventoBanco instanceof EventoFormatura evento) {
                EventoResponseDto eventoResponseDto = EventoResponseDto.fromEvento(evento);
                return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
            } else {

                // Caso o tipo de evento não seja EventoFormatura, trate de forma apropriada
                // Por exemplo, retornar um erro ou um DTO mais genérico

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar evento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
