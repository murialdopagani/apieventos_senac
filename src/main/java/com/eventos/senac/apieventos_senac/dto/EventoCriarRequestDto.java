package com.eventos.senac.apieventos_senac.dto;

public record EventoCriarRequestDto(String nome, String data, int capacidadeMaxima, Long organizadorId) {
}
