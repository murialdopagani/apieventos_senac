package com.eventos.senac.apieventos_senac.dto;

public record EventoCriarRequestDto(String nome, String data, int capacidadeMaxima, Long organizadorId, Long localCerimonia,int tipoEvento,
                                    String instituicao, String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                    String paraninfo, String orador, boolean temCerimonialista) {

}
