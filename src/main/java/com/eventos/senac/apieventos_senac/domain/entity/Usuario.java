package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.requestDto.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.domain.valueobjects.Cpf;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Embedded
    private Cpf cpf;

    private String telefone;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    public Usuario() {
    }

    public Usuario(UsuarioCriarRequestDto usuarioDto) {
        this.nome = usuarioDto.nome();
        this.email = usuarioDto.email();
        this.senha = usuarioDto.senha();
        this.cpf = new Cpf(usuarioDto.cpf());
        this.telefone = usuarioDto.telefone();
        this.status = EnumStatusUsuario.ATIVO;
    }

    public Usuario(Long id,
                   String nome,
                   String email,
                   String senha,
                   Cpf cpf,
                   String telefone) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
    }

    public Usuario atualizarUsuarioFromDTO(Usuario usuarioBanco,
                                           UsuarioCriarRequestDto dto) {
        usuarioBanco.setCpf(new Cpf(dto.cpf()));
        usuarioBanco.setNome(dto.nome());
        usuarioBanco.setSenha(dto.senha());
        usuarioBanco.setEmail(dto.email());
        usuarioBanco.setTelefone(dto.telefone());
        return usuarioBanco;
    }

    public String apresentar() {
        return "Dados " + this.nome + " Cpf Format : " + this.cpf.toString();
    }

}
