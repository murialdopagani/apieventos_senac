package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.application.services.UsuarioService;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/usuario")
@Tag(name = "Usuário Controler", description = "Controladora responsável por gerenciar os Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os usuário.")
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de usuário por id",
        description = "Método responsável por consultar um único usuário por id, se não existir retorna null..!")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) throws RegistroNaoEncontradoException {

        var usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Criar/Atualiza usuario", description = "Método resposável por criar um usuário")
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@RequestBody UsuarioCriarRequestDto usuarioRequestDto) {
        var usuarioSalvo = usuarioService.salvarUsuario(usuarioRequestDto);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Usuário", description = "Método responsável por atualizar um Usuário no banco de dados.")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuario(@PathVariable Long id,
        @RequestBody UsuarioCriarRequestDto usuarioRequestDto) {

        var usuarioBanco = usuarioService.atualizarUsuario(id, usuarioRequestDto);
        return ResponseEntity.ok(usuarioBanco);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete de usuário!", description = "Método responsavel por deletar um usuario")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        return usuarioService.excluirUsuario(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloqueio de usuário!", description = "Método responsavel por Bloquear um usuario")
    public ResponseEntity<?> atualizarBloquear(@PathVariable Long id)  {

        return usuarioService.bloquearUsuario(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/desbloquear")
    @Operation(summary = "Desbloqueio de usuário!", description = "Método responsavel por Desbloquear um usuario")
    public ResponseEntity<?> atualizarDesbloquear(@PathVariable Long id)  {

        return usuarioService.desbloquearUsuario(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
