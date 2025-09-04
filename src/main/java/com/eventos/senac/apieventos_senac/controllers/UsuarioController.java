package com.eventos.senac.apieventos_senac.controllers;

import com.eventos.senac.apieventos_senac.dto.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuário Controler", description = "Controladora responsável por gerenciar os Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping(value = "/salvarUsuario")
    @Operation(summary = "Salva um Usuário", description = "Método responsável por salvar um Usuário no banco de dados.")
    public ResponseEntity<UsuarioResponseDto> salvarUsuario(@RequestBody UsuarioCriarRequestDto usuarioRequestDto) {

        try {
            Usuario usuarioSalvo = usuarioRepository.save(new Usuario(usuarioRequestDto));
            //UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto(usuarioSalvo);
            UsuarioResponseDto usuarioResponseDto = UsuarioResponseDto.fromUsuario(usuarioSalvo);

            return new ResponseEntity<UsuarioResponseDto>(usuarioResponseDto, HttpStatus.OK);
            //return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDto);
        } catch (Exception e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //endregion

    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de usuário por id", description = "Método responsável por consultar um único usuário por id, se não existir retorna null")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

        var usuario = usuarioRepository.findById(id).orElse(null);
        return ResponseEntity.ok(usuario);

    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário por id", description = "Método para deletar um usuário por id")
    public ResponseEntity<?> deletarPoId(@PathVariable Long id){

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }


    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios(){

        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);

    }

//    @PostMapping
//    @Operation(summary = "Criar usuário", description = "Método para criar um usuário")
//    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioCriarRequestDto usuario){
//
//        return  ResponseEntity.ok(new Usuario(usuario));
//    }


}
