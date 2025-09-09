package com.eventos.senac.apieventos_senac.service;

import com.eventos.senac.apieventos_senac.dto.EventoFormaturaCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.repository.EventoFormaturaRepository;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EventoFormaturaService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private EventoFormaturaRepository eventoFormaturaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EventoFormatura criarEventoFormatura(EventoFormaturaCriarRequestDto eventoFormaturaCriarRequestDto) {
        Usuario organizador = usuarioRepository.findById(eventoFormaturaCriarRequestDto.organizadorId())
                                               .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        EventoFormatura eventoFormatura = new EventoFormatura(eventoFormaturaCriarRequestDto, organizador);

        return eventoFormaturaRepository.save(eventoFormatura);
    }
}
