package com.eventos.senac.apieventos_senac.model.entity;

import com.eventos.senac.apieventos_senac.dto.UsuarioCriarRequestDto;
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

    public Usuario(UsuarioCriarRequestDto usuario){
        this.email = usuario.email();
        this.senha = usuario.senha();
        this.cpf = new Cpf(usuario.Cpf());
        this.nome = usuario.nome();
    }


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
