package com.eventos.senac.apieventos_senac.service;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.model.entity.*;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumTipoEvento;
import com.eventos.senac.apieventos_senac.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.repository.LocalCerimoniaRepository;
import com.eventos.senac.apieventos_senac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LocalCerimoniaRepository localCerimoniaRepository;

    public Evento criarEvento(EventoCriarRequestDto eventoCriarRequestDto) {
        Usuario organizador = usuarioRepository.findById(eventoCriarRequestDto.organizadorId())
                                               .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        LocalCerimonia localCerimonia = localCerimoniaRepository.findById(eventoCriarRequestDto.localCerimonia())
                                               .orElseThrow(() -> new RuntimeException("Local de cerimônia não encontrado"));

        validarDataEvento(eventoCriarRequestDto.data());
        Evento evento = criarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador, localCerimonia);

        return eventoRepository.save(evento);
    }

    // Metodo Factory para criar a instância do evento
    private Evento criarEventoBaseadoNoTipo(EventoCriarRequestDto dto, Usuario organizador, LocalCerimonia localCerimonia) {

        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());

        return switch (tipoEvento) {
            case FORMATURA -> new EventoFormatura(dto, organizador, localCerimonia);
            //case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia);
            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
        };
    }

    public List<Evento> listarTodosEventos() {
        return eventoRepository.findAllByOrderByDataAsc();
    }



    public Evento incrementarInscritos(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId).orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        if (evento.isFull()) {
            throw new RuntimeException("Evento lotado");
        }

        if (evento.isPast()) {
            throw new RuntimeException("Não é possível se inscrever em evento já realizado");
        }
        evento.setInscritos(evento.getInscritos() + 1);
        return eventoRepository.save(evento);
    }

    private void validarDataEvento(String dataString) {
        try {
            LocalDateTime dataEvento = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            LocalDateTime hoje = LocalDate.now().atStartOfDay();

            if (dataEvento.isBefore(hoje)) {
                throw new RuntimeException("Não é possível criar evento com data no passado");
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de data inválido");
        }
    }

}
