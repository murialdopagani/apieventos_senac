package com.eventos.senac.apieventos_senac.domain.repository;

import com.eventos.senac.apieventos_senac.domain.entity.Inscricao;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusPresenca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    Optional<Inscricao> findByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

    Optional<Inscricao> findByIdAndStatusPresencaNot(Long inscricaoId, EnumStatusPresenca statusPresenca);

    List<Inscricao> findByEventoId(Long eventoId);
}
