package com.eventos.senac.apieventos_senac.repository;

import com.eventos.senac.apieventos_senac.model.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusLocalCerimonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalCerimoniaRepository extends JpaRepository<LocalCerimonia, Long> {

    Optional <LocalCerimonia> findByCnpj_CnpjAndStatusNot(String cnpj, EnumStatusLocalCerimonia status);




}
