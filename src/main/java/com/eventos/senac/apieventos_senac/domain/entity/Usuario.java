package com.eventos.senac.apieventos_senac.domain.entity;

import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.domain.valueobjects.CPF;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Embedded
    private CPF cpf;

    private String telefone;

    @Column(name = "tipo_usuario", insertable = false, updatable = false, nullable = true)
    private String tipo_usuario;

    @Column
    private boolean acessoIrrestrito = false;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    public Usuario(UsuarioCriarRequestDto usuarioDto) {
        this.nome = usuarioDto.nome();
        this.email = usuarioDto.email();
        this.senha = usuarioDto.senha();
        this.cpf = new CPF(usuarioDto.cpf());
        this.telefone = usuarioDto.telefone();
        this.status = EnumStatusUsuario.ATIVO;
    }

    public Usuario(Long id,
        String nome,
        String email,
        String senha,
        CPF cpf,
        String telefone) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
    }

    public Usuario atualizarUsuarioFromDTO(Usuario usuarioBanco, UsuarioCriarRequestDto dto) {
        usuarioBanco.setCpf(new CPF(dto.cpf()));
        usuarioBanco.setNome(dto.nome());
        usuarioBanco.setSenha(dto.senha());
        usuarioBanco.setEmail(dto.email());
        usuarioBanco.setTelefone(dto.telefone());
        return usuarioBanco;
    }

    public String apresentar() {
        return "Dados " + this.nome + " Cpf Format : " + this.cpf.toString();
    }

    public boolean isAcessoIrrestrito() {
        return acessoIrrestrito;
    }

    public void setAcessoIrrestrito(boolean acessoIrrestrito) {
        this.acessoIrrestrito = acessoIrrestrito;
    }
}
