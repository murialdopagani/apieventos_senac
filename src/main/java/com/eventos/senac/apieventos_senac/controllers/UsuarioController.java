package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusUsuario;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário Controler", description = "Controladora responsável por gerenciar os Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Método para listar todos os usuário.")
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos() {

        var usuarios = usuarioRepository.findAllByStatusNot(EnumStatusUsuario.EXCLUIDO);

        List<UsuarioResponseDto> usuarioResponseDto = usuarios.stream()
                                                              .map(UsuarioResponseDto::fromUsuario)
                                                              .collect(Collectors.toList());
        return ResponseEntity.ok(usuarioResponseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de usuário por id",
            description = "Método responsável por consultar um único usuário por id, se não existir retorna null..!")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {

        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
                                       .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioResponseDto.fromUsuario(usuario));
    }

    @PostMapping
    @Operation(summary = "Criar usuario", description = "Método resposável por criar um usuário")
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@RequestBody UsuarioCriarRequestDto usuarioRequestDto) {

        var usuarioBanco = usuarioRepository.findByCpf_CpfAndStatusNot(String.valueOf(new Cpf(usuarioRequestDto.cpf())),
                EnumStatusUsuario.EXCLUIDO).orElse(new Usuario(usuarioRequestDto));

        if (usuarioBanco.getId() != null) {
            usuarioBanco = usuarioBanco.atualizarUsuarioFromDTO(usuarioBanco, usuarioRequestDto);
        }

        usuarioRepository.save(usuarioBanco);
        UsuarioResponseDto usuarioResponseDto = UsuarioResponseDto.fromUsuario(usuarioBanco);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDto);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Usuário", description = "Método responsável por atualizar um Usuário no banco de dados.")
    public ResponseEntity<UsuarioResponseDto> atulizarUsuario(@PathVariable Long id,
                                                              @RequestBody UsuarioCriarRequestDto usuarioRequestDto) {

        var usuarioBanco = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO).orElse(null);

        if (usuarioBanco == null) {
            return ResponseEntity.notFound().build();
        }

        var usuarioSave = usuarioBanco.atualizarUsuarioFromDTO(usuarioBanco, usuarioRequestDto);

        usuarioRepository.save(usuarioSave);
        UsuarioResponseDto usuarioResponseDto = UsuarioResponseDto.fromUsuario(usuarioSave);

        return ResponseEntity.ok(usuarioResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete de usuário!", description = "Método responsavel por deletar um usuario")
    public ResponseEntity<UsuarioResponseDto> deletarUsuario(@PathVariable Long id) {

        var usuario = usuarioRepository.findById(id)
                                       .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id ->" + id));

        usuario.setStatus(EnumStatusUsuario.EXCLUIDO);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(UsuarioResponseDto.fromUsuario(usuario));
    }

    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloqueio de usuário!", description = "Método responsavel por Bloquear um usuario")
    public ResponseEntity<UsuarioResponseDto> atualizarBloquear(@PathVariable Long id) {

        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setStatus(EnumStatusUsuario.BLOQUEADO);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desbloquear")
    @Operation(summary = "Desbloqueio de usuário!", description = "Método responsavel por Desbloquear um usuario")
    public ResponseEntity<UsuarioResponseDto> atualizarDesbloquear(@PathVariable Long id) {

        var usuario = usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setStatus(EnumStatusUsuario.ATIVO);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
