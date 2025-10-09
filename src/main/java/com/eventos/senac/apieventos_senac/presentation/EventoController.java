package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoFormaturaRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoPalestraRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoResponseDto;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.domain.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.application.services.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PostMapping("/formatura")
    @Operation(summary = "Cria/Atualizar um Evento Formatura", description = "Método responsável por criar/atualizar um Evento Formatura no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoFormaturaRequestDto eventoFormaturaDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromFormaturaDto(eventoFormaturaDto);
        var eventoBanco = eventoService.criarEvento(eventoRequestDto);
        EventoResponseDto eventoResponseDto = EventoResponseDto.fromEvento(eventoBanco);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
    }


    @PutMapping("/formatura/{id}")
    @Operation(summary = "Atualiza um Evento Formatura por ID", description = "Método responsável por atualizar um Evento Formatura por ID.")
    public ResponseEntity<EventoResponseDto> atualizaEventoFormatura(@PathVariable Long id,
        @RequestBody EventoFormaturaRequestDto eventoFormaturaDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromFormaturaDto(eventoFormaturaDto);
        var eventoSave = eventoService.atualizarEvento(id, eventoRequestDto);
        return ResponseEntity.ok(EventoResponseDto.fromEvento(eventoSave));
    }

    @PostMapping("/palestra")
    @Operation(summary = "Cria/Atualizar um Evento Palestra", description = "Método responsável por criar/atualizar um Evento Palestra no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoPalestraRequestDto eventoPalestraDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromPalestraDto(eventoPalestraDto);
        var eventoBanco = eventoService.criarEvento(eventoRequestDto);
        EventoResponseDto eventoResponseDto = EventoResponseDto.fromEvento(eventoBanco);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
    }

    @PutMapping("/palestra/{id}")
    @Operation(summary = "Atualiza um Evento Paletra por ID", description = "Método responsável por atualizar um Evento Palestra por ID.")
    public ResponseEntity<EventoResponseDto> atualizaEventoPalestra(@PathVariable Long id,
        @RequestBody EventoPalestraRequestDto eventoPalestraDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromPalestraDto(eventoPalestraDto);
        var eventoSave = eventoService.atualizarEvento(id, eventoRequestDto);
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
    @Operation(summary = "Consulta de evento por ID",
        description = "Método responsável por consultar um único evento por id, se não existir retorna null..!")
    public ResponseEntity<EventoResponseDto> listarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {
        var evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO).orElseThrow(
            () -> new RegistroNaoEncontradoException("Evento não encontrado"));
        return ResponseEntity.ok(EventoResponseDto.fromEvento(evento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento por ID", description = "Método responsável por deletar um único evento por id.")
    public ResponseEntity<EventoResponseDto> deletarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {
        var evento = eventoRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.EXCLUIDO);
        eventoRepository.save(evento);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar um evento por ID", description = "Método responsável por cancelar um único evento por id.")
    public ResponseEntity<EventoResponseDto> atualizarBloquerar(@PathVariable Long id) throws RegistroNaoEncontradoException {

        var evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO).orElseThrow(() ->
            new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.CANCELADO);
        eventoRepository.save(evento);
        return ResponseEntity.ok(EventoResponseDto.fromEvento(evento));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar um evento por ID", description = "Método responsável por ativar um único evento por id.")
    public ResponseEntity<EventoResponseDto> atualizarAtivar(@PathVariable Long id) throws RegistroNaoEncontradoException {

        var evento = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO).orElseThrow(() ->
            new RegistroNaoEncontradoException("Evento não encontrado"));
        evento.setStatus(EnumStatusEvento.ATIVO);
        eventoRepository.save(evento);
        return ResponseEntity.ok(EventoResponseDto.fromEvento(evento));
    }


}
