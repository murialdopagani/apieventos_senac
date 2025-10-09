package com.eventos.senac.apieventos_senac.domain.repository;

import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsUsuarioByCpf_CpfAndSenhaContaining(String Cpf, String Senha);

    Optional<Usuario> findByCpf_CpfAndStatusNot(String cpf, EnumStatusUsuario status);

    boolean existsUsuarioByEmailContainingAndSenhaAndStatusNot(String email, String senha, EnumStatusUsuario status);

    Optional<Usuario> findByIdAndStatusNot(Long id, EnumStatusUsuario status);

    List<Usuario> findAllByStatusNotOrderById(EnumStatusUsuario status);

    Optional<Usuario> findByEmail(String email);

}
