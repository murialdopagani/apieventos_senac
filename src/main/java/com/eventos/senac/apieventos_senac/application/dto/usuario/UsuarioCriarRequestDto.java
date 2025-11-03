package com.eventos.senac.apieventos_senac.application.dto.usuario;

import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumTipoUsuario;

public record UsuarioCriarRequestDto(String nome, String email, String senha, String cpf, String telefone, EnumTipoUsuario tipoUsuario) {

}
