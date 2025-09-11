package com.eventos.senac.apieventos_senac.dto;

import com.eventos.senac.apieventos_senac.model.entity.Evento;

import java.util.List;

public record UsuarioCriarRequestDto(String nome, String email, String senha, String cpf, String telefone){

}
