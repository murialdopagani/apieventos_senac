package com.eventos.senac.apieventos_senac.services;

import com.eventos.senac.apieventos_senac.dto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.model.entity.Evento;
import com.eventos.senac.apieventos_senac.model.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.model.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.model.entity.Usuario;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.model.valueobjects.EnumStatusUsuario;
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

    public Evento criarEvento(EventoCriarRequestDto eventoCriarRequestDto) throws RegistroNaoEncontradoException {
        Usuario organizador = usuarioRepository.findByIdAndStatusNot(eventoCriarRequestDto.organizadorId(),
                        EnumStatusUsuario.EXCLUIDO)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado.!!"));

        validarDataEvento(eventoCriarRequestDto.data());

        LocalCerimonia localCerimonia = localCerimoniaRepository.findByIdAndStatusNot(eventoCriarRequestDto.localCerimonia(),
                        EnumStatusLocalCerimonia.EXCLUIDO)
                .orElseThrow(() -> new RuntimeException("Local de cerimônia não encontrado"));

        var eventoBanco = eventoRepository.findByDataAndOrganizadorAndLocalCerimoniaAndStatusNot(
                LocalDate.parse(eventoCriarRequestDto.data(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        .atStartOfDay(), organizador, localCerimonia, EnumStatusEvento.EXCLUIDO);

        if (eventoBanco.isPresent()) {
            Evento eventoExistente = eventoBanco.get();
            Evento eventoAtualizado = atualizarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador, localCerimonia,
                    eventoExistente);
            return eventoRepository.save(eventoAtualizado);
        } else {
            Evento evento = criarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador, localCerimonia);
            return eventoRepository.save(evento);
        }
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

    // Metodo Factory para atualizar um evento
    private Evento atualizarEventoBaseadoNoTipo(EventoCriarRequestDto dto,
                                                Usuario organizador,
                                                LocalCerimonia localCerimonia,
                                                Evento eventoBanco) {
        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());

        return switch (tipoEvento) {
            case FORMATURA ->
                    new EventoFormatura(dto, organizador, localCerimonia).atualizarEventoFromDTO((EventoFormatura) eventoBanco,
                            dto, organizador, localCerimonia);
            //case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia);
            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
        };
    }

    private List<Evento> listarTodosEventos() {
        return eventoRepository.findAllByOrderByDataAsc();
    }

    private Evento incrementarInscritos(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        if (evento.isFull()) {
            throw new RuntimeException("Evento lotado");
        }

        if (evento.isPast()) {
            throw new RuntimeException("Não é possível se inscrever em evento já realizado");
        }
        evento.setInscritos(evento.getInscritos() + 1);
        return eventoRepository.save(evento);
    }

    public void validarDataEvento(String dataString) {
        try {
            LocalDateTime dataEvento = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .atStartOfDay();
            LocalDateTime hoje = LocalDate.now()
                    .atStartOfDay();

            if (dataEvento.isBefore(hoje)) {
                throw new RuntimeException("Não é possível criar evento com data no passado");
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de data inválido");
        }
    }

}
