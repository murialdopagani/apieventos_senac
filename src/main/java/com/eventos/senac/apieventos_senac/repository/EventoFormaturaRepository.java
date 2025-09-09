package com.eventos.senac.apieventos_senac.repository;

import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoFormaturaRepository extends JpaRepository<EventoFormatura, Long> {

}
