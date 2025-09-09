package com.eventos.senac.apieventos_senac.dto;

public record EventoFormaturaCriarRequestDto(String nome, String data, int capacidadeMaxima, Long organizadorId, String instituicao,
                                             String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                             String paraninfo, String orador, boolean temCerimonialista, String localCerimonia) {

}
