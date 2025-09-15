package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.EventoResponseDto;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evento")
@Tag(name = "Evento Controller", description = "Controladora responsável por gerenciar os Eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoRepository eventoRepository;

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

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os eventos.")
    public ResponseEntity<List<EventoResponseDto>> listarTodos() {

        var eventos = eventoRepository.findAllByStatusNotOrderById(EnumStatusEvento.EXCLUIDO);
        List<EventoResponseDto> eventoResponseDto = eventos.stream()
                                                           .map(EventoResponseDto::fromEvento)
                                                           .collect(Collectors.toList());
        return ResponseEntity.ok(eventoResponseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de evento por id",
            description = "Método responsável por consultar um único evento por id, se não existir retorna null..!")
    public ResponseEntity<EventoResponseDto> listarPorId(@PathVariable Long id) {
        var evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO).orElse(null);

        if(evento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EventoResponseDto.fromEvento(evento));
    }

}
