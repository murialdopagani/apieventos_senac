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

    boolean existsUsuarioByEmailContainingAndSenha(String email, String senha);

    // 1. findById ignorando status = EXCLUIDO
    Optional<Usuario> findByIdAndStatusNot(Long id, EnumStatusUsuario status);

    // 2. findAll ignorando status = EXCLUIDO
    List<Usuario> findAllByStatusNot(EnumStatusUsuario status);

}
