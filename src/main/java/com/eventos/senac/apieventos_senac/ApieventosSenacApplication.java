package com.eventos.senac.apieventos_senac;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.eventos.senac.apieventos_senac.model.entity.Administrador;
import com.eventos.senac.apieventos_senac.model.entity.Cliente;
import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.model.entity.EventoShow;
import com.eventos.senac.apieventos_senac.model.entity.EventoWorkshop;
import com.eventos.senac.apieventos_senac.model.entity.NotificacaoEmail;
import com.eventos.senac.apieventos_senac.model.entity.NotificacaoSMS;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;

@SpringBootApplication
public class ApieventosSenacApplication {

    public static void main(String[] args) {

        var usuario = new Usuario(1L, "Murialdo Pagani", "mrialdo.pagani@gmail.com", "123", new Cpf("986.275.209-25"),"+5548984045098");
        //System.out.println(usuario.apresentar());

        var administrador = new Administrador(2L, "Daniel Pagani", "teste", "123456", new Cpf("996.275.209-25"),"+5548984045098");
        administrador.setAcessoIrrestrito(true);
        administrador.setAcessoIrrestrito(false);
        administrador.setNome("Nome Alterado");
        administrador.setTelefone("+5548984045098");
        //System.out.println(administrador.apresentar());

        var cliente = new Cliente(3L, "Gilnete Pagani", "teste@teste.com", "123456", new Cpf("996.275.209-25"), new java.math.BigDecimal("100.00"), "+5548984045098");

        //System.out.println(cliente.apresentar());

        var evento = new Evento(1L, "Evento de Teste", java.time.LocalDateTime.of(2025, 8, 6, 21, 54), 100, usuario, 0);
        //System.out.println("Evento: " + evento.getNome() + ", Organizador: " + usuario.getNome() + ", Data: " + evento.getData());

        var eventoFormatura = new EventoFormatura();

        var eventoShow = new EventoShow();

        var eventoPalestra = new EventoPalestra();

        var eventoWorkshop = new EventoWorkshop(1L, "Workshop de Teste", java.time.LocalDateTime.of(2025, 8, 6, 21, 54), 100, usuario, 0, "Instrutor Teste", "Tema Teste", "Tecnologia", 120, new java.math.BigDecimal("50.00"), "Nenhum", true);

        NotificacaoEmail notificacaoEmail = new NotificacaoEmail(usuario.getEmail());
        notificacaoEmail.Enviar("Sua Nova senha é 123456");
        
        NotificacaoSMS notificacaoSMS = new NotificacaoSMS(administrador.getTelefone());
        notificacaoSMS.Enviar("Acesso indevido");
        
        
        //SpringApplication.run(ApieventosSenacApplication.class, args);
    }

}
