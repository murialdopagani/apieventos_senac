package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuário Controler", description = "Controladora responsável por gerenciar os Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/salvarUsuario")
    @Operation(summary = "Salva um Usuário", description = "Método responsável por salvar um Usuário no banco de dados.")
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody UsuarioCriarRequestDto usuario) {

        Usuario usuarioSalvo = usuarioRepository.save(new Usuario(usuario));

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de usuário por id", description = "Método responsável por consultar um único usuário por id, se não existir retorna null")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioRepository.findById(id).orElse(null);

        return ResponseEntity.ok(usuario);

    }

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Método para criar um usuário")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioCriarRequestDto usuario){

        return  ResponseEntity.ok(new Usuario(usuario));
    }

    //http://localhost:8080/swagger-ui/index.html#/usuario-controller
}
