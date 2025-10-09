package com.eventos.senac.apieventos_senac.application.dto.usuario;

import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;

// Metodo estático factory
public record UsuarioResponseDto(Long id, String nome, String email, String cpf, String telefone, EnumStatusUsuario status) {

    public UsuarioResponseDto(Usuario usuario) {
        this(usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getCpf().getNumero(),
            usuario.getTelefone(),
            usuario.getStatus());
    }
}




