package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.LocalCerimoniaResponseDto;
import com.eventos.senac.apieventos_senac.model.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cnpj;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.repository.LocalCerimoniaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localCerimonia")
@Tag(name = "Local Cerimônia Controller", description = "Controladora responsável por gerenciar os Locais de Cerimônia")
public class LocalCerimoniaController {

    @Autowired
    private LocalCerimoniaRepository localCerimoniaRepository;

    @PostMapping
    @Operation(summary = "Criar Local Cerimônia", description = "Método para criar um novo local de cerimônia.")
    public ResponseEntity<?> criarLocalCerimonia(@RequestBody @Validated LocalCerimoniaCriarRequestDto requestDto) {

        try {
            var localCerimoniaBanco = localCerimoniaRepository.findByCnpj_CnpjAndStatusNot(
                                                                      String.valueOf(new Cnpj(requestDto.cnpj())), EnumStatusLocalCerimonia.EXCLUIDO)
                                                              .orElse(new LocalCerimonia(requestDto));

            if (localCerimoniaBanco.getId() != null) {
                localCerimoniaBanco = localCerimoniaBanco.atualizarLocalCerimoniaFromDto(localCerimoniaBanco, requestDto);
            }

            localCerimoniaRepository.save(localCerimoniaBanco);
            var localCerimoniaResponseDto = LocalCerimoniaResponseDto.fromLocalCerimonia(localCerimoniaBanco);

            return ResponseEntity.status(HttpStatus.OK).body(localCerimoniaResponseDto);

        } catch (Exception e) {
            System.err.println("Erro ao salvar/atualizar um local de cerimonia: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os locais de cerimônia.")
    public ResponseEntity<List<LocalCerimonia>> listarTodos() {

        List localCerimonia = localCerimoniaRepository.findAll();

        return ResponseEntity.ok(localCerimonia);
    }

}
