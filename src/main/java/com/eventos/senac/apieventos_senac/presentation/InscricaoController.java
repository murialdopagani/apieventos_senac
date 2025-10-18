package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.evento.EventoResponseDto;
import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoRequestDto;
import com.eventos.senac.apieventos_senac.application.services.InscricaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventos/{eventoId}/inscricoes")
@Tag(name = "Inscrições")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping
    public ResponseEntity<EventoResponseDto> inscrever(@PathVariable("eventoId") Long eventoId,
                                                       @Valid @RequestBody InscricaoRequestDto request) {
        EventoResponseDto resp = inscricaoService.inscrever(eventoId, request.usuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> cancelar(@PathVariable("eventoId") Long eventoId,
                                         @PathVariable("usuarioId") Long usuarioId) {
        inscricaoService.cancelarInscricao(eventoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}

