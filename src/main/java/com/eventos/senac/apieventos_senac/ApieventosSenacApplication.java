package com.eventos.senac.apieventos_senac;

import com.eventos.senac.apieventos_senac.model.entities.Administrador;
import com.eventos.senac.apieventos_senac.model.entities.Cliente;
import com.eventos.senac.apieventos_senac.model.entities.Evento;
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
        System.out.println(usuario.apresentar());

        var administrador = new Administrador(2L,"Daniel Pagani","teste","123456",new Cpf("996.275.209-25"));
        administrador.setAcessoIrrestrito(true);
        System.out.println(administrador.apresentar());
        administrador.setAcessoIrrestrito(false);
        administrador.setNome("Nome Alterado");
        System.out.println(administrador.apresentar());

        var cliente = new Cliente(3L,"Gilnete Pagani","teste@teste.com","123456",new Cpf("996.275.209-25"),new java.math.BigDecimal("100.00"));
        System.out.println(cliente.apresentar());

        var evento = new Evento(1L, "Evento de Teste", java.time.LocalDateTime.of(2025,8,6,21,54), 100, usuario, 0);
        System.out.println("Evento: " + evento.getNome() + ", Organizador: " + usuario.getNome() + ", Data: " + evento.getData());


    //SpringApplication.run(ApieventosSenacApplication.class, args);
	}

}
