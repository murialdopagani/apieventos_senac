package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaResponseDto;
import com.eventos.senac.apieventos_senac.application.services.LocalCerimoniaService;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/localCerimonia")
@Tag(name = "Local Cerimônia Controller", description = "Controladora responsável por gerenciar os Locais de Cerimônias")
public class LocalCerimoniaController {

    @Autowired
    private LocalCerimoniaService localCerimoniaService;

    @PostMapping
    @Operation(summary = "Cria/Atualiza Local de Cerimônias", description = "Método para criar ou atualizar um local de cerimônias.")
    public ResponseEntity<LocalCerimoniaResponseDto> criarLocalCerimonia(
        @RequestBody @Validated LocalCerimoniaCriarRequestDto requestDto) {
        LocalCerimoniaResponseDto localCerimonia = localCerimoniaService.salvarLocalCerimonia(requestDto);
        return ResponseEntity.ok(localCerimonia);
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os locais de cerimônias.")
    public ResponseEntity<List<LocalCerimoniaResponseDto>> listarTodos() {
        return ResponseEntity.ok(localCerimoniaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de local de cerimônias por id",
        description = "Método responsável por consultar um único local de cerimônias por id, se não existir retorna null..!")
    public ResponseEntity<LocalCerimoniaResponseDto> buscarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {
        var localCerimoniaResponseDto = localCerimoniaService.buscarPorId(id);
        if (localCerimoniaResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(localCerimoniaResponseDto);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete local de cerimônias !", description = "Método responsavel por deletar um local de cerimônias ")
    public ResponseEntity<?> deletarLocalCerimonia(@PathVariable Long id) {
        return localCerimoniaService.excluirLocalCerimonia(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloqueiolocal de cerimônias !", description = "Método responsavel por Bloquear local de cerimônias !")
    public ResponseEntity<?> bloquearLocalCerimonia(@PathVariable Long id) {
        return localCerimoniaService.bloquearLocalCerimonia(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/desbloquear")
    @Operation(summary = "Desbloqueio local de cerimônias !", description = "Método responsavel por Desbloquear local de cerimônias !")
    public ResponseEntity<?> desbloquearLocalCerimonia(@PathVariable Long id)  {
        return localCerimoniaService.desbloquearLocalCerimonia(id) ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

}
