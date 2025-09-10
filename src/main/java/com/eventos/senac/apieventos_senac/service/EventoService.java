package com.eventos.senac.apieventos_senac.service;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EventoService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EventoFormatura criarEventoFormatura(EventoCriarRequestDto eventoCriarRequestDto) {
        Usuario organizador = usuarioRepository.findById(eventoCriarRequestDto.organizadorId())
                                               .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        EventoFormatura eventoFormatura = new EventoFormatura(eventoCriarRequestDto, organizador);

        return eventoRepository.save(eventoFormatura);
    }
}
