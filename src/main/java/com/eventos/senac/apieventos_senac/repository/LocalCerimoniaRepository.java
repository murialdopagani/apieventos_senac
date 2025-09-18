package com.eventos.senac.apieventos_senac.repository;

import com.eventos.senac.apieventos_senac.model.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalCerimoniaRepository extends JpaRepository<LocalCerimonia, Long> {

    Optional <LocalCerimonia> findByCnpj_CnpjAndStatusNot(String cnpj, EnumStatusLocalCerimonia status);

    List<LocalCerimonia> findAllByStatusNot(EnumStatusLocalCerimonia status);

    Optional <LocalCerimonia> findByIdAndStatusNot(Long id, EnumStatusLocalCerimonia enumStatusLocalCerimonia);
}
