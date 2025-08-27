package com.eventos.senac.apieventos_senac.controller;

import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "/salvarUsuario")
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario)  {

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {

        return new Usuario(1L, "Murialdo", "murialdo@gmail.com", "123", new Cpf("986.275.209-25"), "48-98404-5098");

    }

}
