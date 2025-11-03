package com.eventos.senac.apieventos_senac.application.dto.usuario;

import com.eventos.senac.apieventos_senac.domain.entity.Usuario;

public record UsuarioLogadoDto(Long id, String email) {

    public UsuarioLogadoDto(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getEmail()
        );

    }
}
