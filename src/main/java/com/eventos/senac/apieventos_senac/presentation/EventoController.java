package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoFormaturaRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoPalestraRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoResponseDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoShowRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.evento.EventoWorkshopRequestDto;
import com.eventos.senac.apieventos_senac.application.services.EventoService;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/formatura")
    @Operation(summary = "Cria um Evento Formatura", description = "Método responsável por criar um Evento Formatura no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoFormaturaRequestDto eventoFormaturaDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromFormaturaDto(eventoFormaturaDto);
        EventoResponseDto eventoResponseDto = eventoService.criarEvento(eventoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
    }

    @PutMapping("/formatura/{id}")
    @Operation(summary = "Atualiza um Evento Formatura por ID", description = "Método responsável por atualizar um Evento Formatura por ID.")
    public ResponseEntity<EventoResponseDto> atualizaEventoFormatura(@PathVariable Long id,
        @RequestBody EventoFormaturaRequestDto eventoFormaturaDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromFormaturaDto(eventoFormaturaDto);
        EventoResponseDto eventoResponseDto = eventoService.atualizarEvento(id, eventoRequestDto);
        return ResponseEntity.ok(eventoResponseDto);
    }

    @PostMapping("/palestra")
    @Operation(summary = "Cria um Evento Palestra", description = "Método responsável por criar um Evento Palestra no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoPalestraRequestDto eventoPalestraDto)
        throws Exception {

        EventoRequestDto eventoRequestDto = EventoRequestDto.fromPalestraDto(eventoPalestraDto);
        EventoResponseDto eventoResponseDto = eventoService.criarEvento(eventoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
    }

    @PutMapping("/palestra/{id}")
    @Operation(summary = "Atualiza um Evento Paletra por ID", description = "Método responsável por atualizar um Evento Palestra por ID.")
    public ResponseEntity<EventoResponseDto> atualizaEventoPalestra(@PathVariable Long id,
        @RequestBody EventoPalestraRequestDto eventoPalestraDto)
        throws Exception {
        EventoRequestDto eventoRequestDto = EventoRequestDto.fromPalestraDto(eventoPalestraDto);
        EventoResponseDto eventoResponseDto = eventoService.atualizarEvento(id, eventoRequestDto);
        return ResponseEntity.ok(eventoResponseDto);

    }

    @PostMapping("/show")
    @Operation(summary = "Cria um Evento Show", description = "Método responsável por criar um Evento Show no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoShowRequestDto eventoShowDto)
        throws Exception {

        EventoRequestDto eventoRequestDto = EventoRequestDto.fromShowDto(eventoShowDto);
        EventoResponseDto eventoResponseDto = eventoService.criarEvento(eventoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
    }

    @PutMapping("/show/{id}")
    @Operation(summary = "Atualiza um Evento Show por ID", description = "Método responsável por atualizar um Evento Show por ID.")
    public ResponseEntity<EventoResponseDto> atualizaEventoShow(@PathVariable Long id,
        @RequestBody EventoShowRequestDto eventoShowDto)
        throws Exception {

        EventoRequestDto eventoRequestDto = EventoRequestDto.fromShowDto(eventoShowDto);
        EventoResponseDto eventoResponseDto = eventoService.atualizarEvento(id, eventoRequestDto);
        return ResponseEntity.ok(eventoResponseDto);

    }

    @PostMapping("/workshop")
    @Operation(summary = "Cria um Evento Workshop", description = "Método responsável por criar um Evento Workshop no sistema.")
    public ResponseEntity<EventoResponseDto> criarEvento(@RequestBody EventoWorkshopRequestDto eventoWorkshopDto)
        throws Exception {

        EventoRequestDto eventoRequestDto = EventoRequestDto.fromWorkshopDto(eventoWorkshopDto);
        EventoResponseDto eventoResponseDto = eventoService.criarEvento(eventoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoResponseDto);
    }

    @PutMapping("/workshop/{id}")
    @Operation(summary = "Atualiza um Evento Workshop", description = "Método responsável por atualizar um Evento Workshop no sistema.")
    public ResponseEntity<EventoResponseDto> atualizaEventoShow(@PathVariable Long id,
        @RequestBody EventoWorkshopRequestDto eventoWorkshopDto)
        throws Exception {

        EventoRequestDto eventoRequestDto = EventoRequestDto.fromWorkshopDto(eventoWorkshopDto);
        EventoResponseDto eventoResponseDto = eventoService.atualizarEvento(id, eventoRequestDto);
        return ResponseEntity.ok(eventoResponseDto);

    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os eventos.")
    public ResponseEntity<List<EventoResponseDto>> listarTodos() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de evento por ID", description = "Método responsável por consultar um único evento por id, se não existir retorna null..!")
    public ResponseEntity<EventoResponseDto> listarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {
        var evento = eventoService.buscarEventoPorId(id);
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evento);
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar um evento por ID", description = "Método responsável por cancelar um único evento por id.")
    public ResponseEntity<?> cancelar(@PathVariable Long id) throws RegistroNaoEncontradoException {
        return eventoService.excluirEvento(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar um evento por ID", description = "Método responsável por ativar um único evento por id.")
    public ResponseEntity<?> atualizarAtivar(@PathVariable Long id) throws RegistroNaoEncontradoException {
        return eventoService.ativar(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
