package com.eventos.senac.apieventos_senac.dto.requestDto;

public record EventoCriarRequestDto(
    int tipoEvento,
    String nome,
    String data,
    int capacidadeMaxima,
    Long organizadorId,
    Long localCerimoniaId,
    String instituicao,
    String curso,
    int anoFormatura,
    String grauAcademico,
    int numeroFormandos,
    String paraninfo,
    String orador,
    boolean temCerimonialista) {

    public static EventoCriarRequestDto fromFormaturaDto(EventoFormaturaRequestDto dto) {
        return new EventoCriarRequestDto(
            dto.tipoEvento(),
            dto.nome(),
            dto.data(),
            dto.capacidadeMaxima(),
            dto.organizadorId(),
            dto.localCerimoniaId(),
            dto.instituicao(),
            dto.curso(),
            dto.anoFormatura(),
            dto.grauAcademico(),
            dto.numeroFormandos(),
            dto.paraninfo(),
            dto.orador(),
            dto.temCerimonialista()
        );
    }



}

