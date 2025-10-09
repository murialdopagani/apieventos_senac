package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.valueobjects.Cnpj;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.repository.LocalCerimoniaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            System.err.println("Erro ao salvar/atualizar um local de cerimonia: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os locais de cerimônia.")
    public ResponseEntity<?> listarTodos() {
        try {
            var localCerimonia = localCerimoniaRepository.findAllByStatusNot(EnumStatusLocalCerimonia.EXCLUIDO);
            List<LocalCerimoniaResponseDto> localCerimoniaResponseDto = localCerimonia.stream()
                                                                                      .map(LocalCerimoniaResponseDto::fromLocalCerimonia)
                                                                                      .collect(Collectors.toList());
            return ResponseEntity.ok(localCerimoniaResponseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de local de cerimônia por id",
            description = "Método responsável por consultar um único local de cerimônia por id, se não existir retorna null..!")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

        try {
            var localCerimoniaBanco = localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO)
                                                              .orElse(null);
            if (localCerimoniaBanco == null) {
                return ResponseEntity.notFound().build();
            }
            var localCerimoniaResponseDto = LocalCerimoniaResponseDto.fromLocalCerimonia(localCerimoniaBanco);
            return ResponseEntity.ok(localCerimoniaResponseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}
