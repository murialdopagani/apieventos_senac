package com.eventos.senac.apieventos_senac.repository;

import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByCpf_CpfAndSenhaContaining(String Cpf, String Senha);

    Optional<Usuario> findByCpf_CpfAndStatusNot(String cpf, EnumStatusUsuario status);

    boolean existsUsuarioByEmailContainingAndSenhaAndStatusNot(String email, String senha, EnumStatusUsuario status);

    Optional<Usuario> findByIdAndStatusNot(Long id, EnumStatusUsuario status);

    List<Usuario> findAllByStatusNot(EnumStatusUsuario status);

    Optional<Usuario> findByEmail(String email);

}
