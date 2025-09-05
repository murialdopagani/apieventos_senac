package com.eventos.senac.apieventos_senac.dto;

public record EventoResponseDto(Long id, String nome, String data, int capacidadeMaxima, int inscritos, Long organizadorId) {

    public static EventoResponseDto fromEvento(com.eventos.senac.apieventos_senac.model.entity.Evento evento) {
        return new EventoResponseDto(evento.getId(),
                evento.getNome(),
                evento.getData().toString(),
                evento.getCapacidadeMaxima(),
                evento.getInscritos(),
                evento.getOrganizador().getId());
    }

}
