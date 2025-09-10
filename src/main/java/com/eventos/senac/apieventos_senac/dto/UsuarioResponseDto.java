package com.eventos.senac.apieventos_senac.dto;

import com.eventos.senac.apieventos_senac.model.entity.Usuario;

public record UsuarioResponseDto(Long id, String nome, String email, String cpf, String telefone) {

    // Construtor que recebe um Usuario (factory method)
    //    private UsuarioResponseDto(Usuario usuario) {
    //        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCpf() != null ? usuario.getCpf().toString() : null,
    //                usuario.getTelefone());
    //    }

    // Metodo estático factory
    public static UsuarioResponseDto fromUsuario(Usuario usuario) {
        return new UsuarioResponseDto(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getCpf() != null ? usuario.getCpf().toString() : null, usuario.getTelefone());
    }

}
