package com.eventos.senac.apieventos_senac.dto.requestDto;

public record EventoFormaturaRequestDto(
    //atributos comuns da classe pai
    //int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long organizadorId,
    Long localCerimoniaId,

    //atributos especificos da classe filha (Formatura)
    String instituicao,
    String curso,
    int anoFormatura,
    String grauAcademico,
    int numeroFormandos,
    String paraninfo,
    String orador,
    boolean temCerimonialista) {

}
