package com.eventos.senac.apieventos_senac.dto;

import com.eventos.senac.apieventos_senac.model.valueobjects.EnumTipoEvento;

public record EventoCriarRequestDto(String nome, String data, int capacidadeMaxima, Long organizadorId, int tipoEvento,
                                    String instituicao, String curso, int anoFormatura, String grauAcademico, int numeroFormandos,
                                    String paraninfo, String orador, boolean temCerimonialista, String localCerimonia) {

}
