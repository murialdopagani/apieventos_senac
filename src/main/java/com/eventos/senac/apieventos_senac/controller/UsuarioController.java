package com.eventos.senac.apieventos_senac.controller;

import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {

        return new Usuario(1L, "Murialdo", "murialdo@gmail.com", "123", new Cpf("986.275.209-25"), "48-98404-5098");

    };

}
