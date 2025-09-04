package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.dto.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.dto.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import jakarta.persistence.*;
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

    @Column
    @Embedded
    private Cpf cpf;

    @Column(nullable = false)
    private String telefone;

    public Usuario() {
    }

    public Usuario(UsuarioCriarRequestDto usuarioDto) {
        this.nome = usuarioDto.nome();
        this.email = usuarioDto.email();
        this.senha = usuarioDto.senha();
        this.cpf = new Cpf(usuarioDto.Cpf());
        this.telefone = usuarioDto.telefone();
    }

    // Construtor existente com todos os parâmetros
    public Usuario(Long id, String nome, String email, String senha, Cpf cpf, String telefone) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
    }


    public String apresentar() {
        return "Dados " + this.nome + " Cpf Format : " + this.cpf.toString();
    }

}
