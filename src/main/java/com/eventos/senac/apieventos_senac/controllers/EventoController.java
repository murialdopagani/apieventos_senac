package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.EventoResponseDto;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.services.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evento")
@Tag(name = "Evento Controller", description = "Controladora responsável por gerenciar os Eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    @Operation(summary = "Cria/Atualizar um Evento", description = "Método responsável por criar/atualizar um Evento no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoCriarRequestDto eventoCriarRequestDto)
        throws Exception {

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
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Evento", description = "Método responsável por atualizar um Evento por ID.")
    public ResponseEntity<EventoResponseDto> atualizaEvento(@PathVariable Long id,
        @RequestBody EventoCriarRequestDto eventoCriarRequestDto)
        throws Exception {
        var eventoSave = eventoService.atualizarEvento(id, eventoCriarRequestDto);
        return ResponseEntity.ok(EventoResponseDto.fromEvento(eventoSave));
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os eventos.")
    public ResponseEntity<List<EventoResponseDto>> listarTodos() {

        var eventos = eventoRepository.findAllByStatusNotOrderById(EnumStatusEvento.EXCLUIDO);
        List<EventoResponseDto> eventoResponseDto = eventos.stream().map(EventoResponseDto::fromEvento).collect(
            Collectors.toList());
        return ResponseEntity.ok(eventoResponseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de evento por id",
        description = "Método responsável por consultar um único evento por id, se não existir retorna null..!")
    public ResponseEntity<EventoResponseDto> listarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {
        var evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO).orElseThrow(
            () -> new RegistroNaoEncontradoException("Evento não encontrado"));
        return ResponseEntity.ok(EventoResponseDto.fromEvento(evento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento por id", description = "Método responsável por deletar um único evento por id.")
    public ResponseEntity<EventoResponseDto> deletarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {
        var evento = eventoRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.EXCLUIDO);
        eventoRepository.save(evento);
        return ResponseEntity.noContent().build();
    }

}
