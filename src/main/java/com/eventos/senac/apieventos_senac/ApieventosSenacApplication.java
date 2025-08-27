package com.eventos.senac.apieventos_senac;

import com.eventos.senac.apieventos_senac.model.entity.*;
import com.eventos.senac.apieventos_senac.model.valueobjects.Cpf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApieventosSenacApplication {

    public static void main(String[] args) {

//        var usuario = new Usuario(1L, "Murialdo Pagani", "mrialdo.pagani@gmail.com", "123", new Cpf("986.275.209-25"),
//                "+5548984045098");
//        var administrador = new Administrador(2L, "Daniel Pagani", "teste", "123456", new Cpf("996.275.209-25"), "+5548984045098");
//        administrador.setAcessoIrrestrito(true);
//        administrador.setNome("Nome Alterado");
//        administrador.setTelefone("+5548984045098");
//        administrador.setEmail("adm@gmail.com");
//
//        var cliente = new Cliente(3L, "Gilnete Pagani", "teste@teste.com", "123456", new Cpf("996.275.209-25"),
//                new java.math.BigDecimal("100.00"), "+5548984045098");
//
//        var eventoFormatura = new EventoFormatura(2L, "Formatura de Teste", java.time.LocalDateTime.of(2025, 8, 6, 21, 54), 100,
//                usuario, 0, "Instituição Teste", "Curso Teste", 2025, "Graduação", 50, "Paraninfo Teste", "Orador Teste", true,
//                "Local Cerimônia Teste");
//        var eventoShow = new EventoShow(3L, "Show de Teste", java.time.LocalDateTime.of(2025, 8, 6, 21, 54), 100, usuario, 0,
//                "Artista Teste", "Gênero Teste", 120, new java.math.BigDecimal("50.00"), 18, new java.math.BigDecimal("1000.00"));
//        var eventoPalestra = new EventoPalestra(4L, "Palestra de Teste", java.time.LocalDateTime.of(2025, 8, 6, 21, 54), 100,
//                usuario, 0, "Palestrante Teste", "Título de Teste", "Tema de Teste", "Categoria de Teste", 60,
//                "Biografia do Palestrante", 15, true, "Objetivos de Aprendizagem", false, new java.math.BigDecimal("0.00"));
//        var eventoWorkshop = new EventoWorkshop(1L, "Workshop de Teste", java.time.LocalDateTime.of(2025, 8, 6, 21, 54), 100,
//                usuario, 0, "Instrutor Teste", "Tema Teste", "Tecnologia", 120, new java.math.BigDecimal("50.00"), "Nenhum", true);
//
//        var inscricao = new Inscricao(1L, usuario, eventoFormatura, java.time.LocalDateTime.now(), "Observação de Teste",
//                "Pendente", "Normal");
//
//        List<Evento> listaEventos = new ArrayList<>();
//        listaEventos.add(eventoFormatura);
//        listaEventos.add(eventoShow);
//        listaEventos.add(eventoPalestra);
//        listaEventos.add(eventoWorkshop);
//
//        listaEventos.forEach(evento -> {
//            if (evento instanceof EventoFormatura formatura) {
//                System.out.println("Instituição: " + formatura.getInstituicao());
//                System.out.println("Curso: " + formatura.getCurso());
//            } else if (evento instanceof EventoShow show) {
//                System.out.println("Artista: " + show.getArtista());
//                System.out.println("Gênero: " + show.getGeneroMusical());
//            } else if (evento instanceof EventoPalestra palestra) {
//                System.out.println("Palestrante: " + palestra.getPalestrante());
//                System.out.println("Título: " + palestra.getTituloPalestra());
//            } else if (evento instanceof EventoWorkshop workshop) {
//                System.out.println("Instrutor: " + workshop.getInstrutor());
//                System.out.println("Tema: " + workshop.getTema());
//            }
//        });
//
//        listaEventos.add(eventoWorkshop);
        //      listaEventos.remove(eventoFormatura);

        /*
        listaEventos.forEach(evento -> {
            System.out.println("Evento: " + evento.getNome());
        });
*/

//        listaEventos.forEach(System.out::println);

/*
        NotificacaoEmail notificacaoEmail1 = new NotificacaoEmail(usuario.getEmail());
        notificacaoEmail1.Enviar("Sua Nova senha é 123456");
        
        NotificacaoEmail notificacaoEmail2 = new NotificacaoEmail(administrador.getEmail());
        notificacaoEmail2.Enviar("Sua senha foi alterada com sucesso");
        
        NotificacaoSMS notificacaoSMS1 = new NotificacaoSMS(administrador.getTelefone());
        notificacaoSMS1.Enviar("Acesso indevido");
        
        NotificacaoSMS notificacaoSMS2 = new NotificacaoSMS(administrador.getTelefone());
        notificacaoSMS2.Enviar("Acesso autorizado");

*/
        SpringApplication.run(ApieventosSenacApplication.class, args);
    }

}
