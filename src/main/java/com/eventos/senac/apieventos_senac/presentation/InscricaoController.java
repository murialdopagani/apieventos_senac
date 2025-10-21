package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.inscricao.InscricaoResponseDto;
import com.eventos.senac.apieventos_senac.application.services.InscricaoService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inscricao")
@Tag(name = "Inscrições Controller", description = "Controladora responsável por gerenciar as Inscrições em Eventos")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;


    @PostMapping("/inscrever")
    @Operation(summary = "Inscrever usuário em evento", description = "Método responsável por inscrever um usuário em um evento.")
    public ResponseEntity<InscricaoResponseDto> inscrever(@RequestBody InscricaoRequestDto requestDto) {
        InscricaoResponseDto responseDto = inscricaoService.inscrever(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    @Operation(summary = "Listar todas as inscrições", description = "Retorna todas as inscrições cadastradas.")
    public ResponseEntity<List<InscricaoResponseDto>> listarTodos() {
        List<InscricaoResponseDto> lista = inscricaoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/evento/{eventoId}")
    @Operation(summary = "Listar inscrições por evento", description = "Retorna todas as inscrições de um evento específico.")
    public ResponseEntity<List<InscricaoResponseDto>> listarPorEvento(@PathVariable("eventoId") Long eventoId) {
        List<InscricaoResponseDto> lista = inscricaoService.listarPorEvento(eventoId);
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/cancelar/{inscricaoId}")
    @Operation(summary = "Cancelar inscrição de usuário em evento", description = "Método responsável por cancelar a inscrição de um usuário em um evento.")
    public ResponseEntity<InscricaoResponseDto> cancelar(@PathVariable("inscricaoId") Long inscricaoId) {
        InscricaoResponseDto responseDto = inscricaoService.cancelarInscricao(inscricaoId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/confirmar/{inscricaoId}")
    @Operation(summary = "Confirmar inscrição de usuário no evento", description = "Método responsável por confirmar a inscrição de um usuário no evento.")
    public ResponseEntity<InscricaoResponseDto> confirmar(@PathVariable("inscricaoId") Long inscricaoId) {
        InscricaoResponseDto responseDto = inscricaoService.confirmarInscricao(inscricaoId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
