package com.eventos.senac.apieventos_senac.domain.repository;

import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findAllByOrderByDataAsc();

    List<Evento> findAllByStatusNotOrderById(EnumStatusEvento statusEvento);

    Optional<Evento> findByIdAndStatusNot(Long id, EnumStatusEvento enumStatusEvento);

    Object findByData(LocalDateTime data);

    List<Evento> findByDataAndLocalCerimoniaAndStatusNotOrderById(LocalDateTime localDate,
        LocalCerimonia localCerimonia,
        EnumStatusEvento statusEvento);

    List<Evento> findByDataBetweenAndLocalCerimoniaAndStatusNotOrderById(
        LocalDateTime dataIni,
        LocalDateTime dataFim,
        LocalCerimonia localCerimonia,
        EnumStatusEvento status
    );


    Optional<Evento> findByIdAndStatus(Long id, EnumStatusEvento enumStatusEvento);
}
