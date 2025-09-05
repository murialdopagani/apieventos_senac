package com.eventos.senac.apieventos_senac.repository;

import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByCpf_CpfAndSenhaContaining(String Cpf , String Senha);

}
