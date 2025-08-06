package com.eventos.senac.apieventos_senac;

import com.eventos.senac.apieventos_senac.model.entities.Administrador;
import com.eventos.senac.apieventos_senac.model.entities.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApieventosSenacApplication {

	public static void main(String[] args) {
		
		var usuario = new Usuario();
    	usuario.setId(1L);
		usuario.setNome("Murialdo Pagani");
		usuario.setCpf(new Cpf("986.275.209-25"));
        usuario.setEmail("murialdo.pagani@gmail.com");

        var administrador = new Administrador();
        administrador.setId(1L);
        administrador.setNome("Murialdo Pagani");
        administrador.setCpf(new Cpf("996.275.209-25"));
        administrador.setAcessoIrrestrito(true);
        administrador.setEmail("murialdo.pagani@gmail.com");

        System.out.println("Dados "+ usuario.getNome() + "CPF Format : " + usuario.getCpf());
		System.out.println( usuario.apresentarCpf());
        System.out.println(administrador.apresentarCpf());


		//SpringApplication.run(ApieventosSenacApplication.class, args);
	}

}
