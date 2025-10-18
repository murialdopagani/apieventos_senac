package com.eventos.senac.apieventos_senac.domain.repository;

import com.eventos.senac.apieventos_senac.domain.entity.Inscricao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    @Query("select i from Inscricao i where i.evento.id = :eventoId and i.usuario.id = :usuarioId")
    Optional<Inscricao> findByEventoIdAndUsuarioId(@Param("eventoId") Long eventoId, @Param("usuarioId") Long usuarioId);

}

