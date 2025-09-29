package com.eventos.senac.apieventos_senac.repository;

import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findAllByOrderByDataAsc();

    List<Evento> findAllByStatusNotOrderById(EnumStatusEvento statusEvento);

    Optional<Evento> findByIdAndStatusNot(Long id, EnumStatusEvento enumStatusEvento);

    Object findByData(LocalDateTime data);

    List<Evento> findByDataAndOrganizadorAndLocalCerimoniaAndStatusNotOrderById(LocalDateTime localDateTime,
                                                                           Usuario organizador,
                                                                           LocalCerimonia localCerimonia,
                                                                           EnumStatusEvento statusEvento);
}
