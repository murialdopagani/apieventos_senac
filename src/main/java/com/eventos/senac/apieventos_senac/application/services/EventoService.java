package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.requestDto.EventoCriarRequestDto;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.exception.ValidacoesRegraNegocioException;
import com.eventos.senac.apieventos_senac.domain.entity.Evento;
import com.eventos.senac.apieventos_senac.domain.entity.EventoFormatura;
import com.eventos.senac.apieventos_senac.domain.entity.EventoPalestra;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusEvento;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumTipoEvento;
import com.eventos.senac.apieventos_senac.domain.repository.EventoRepository;
import com.eventos.senac.apieventos_senac.domain.repository.LocalCerimoniaRepository;
import com.eventos.senac.apieventos_senac.domain.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LocalCerimoniaRepository localCerimoniaRepository;

    public Evento criarEvento(EventoCriarRequestDto eventoCriarRequestDto) {

        validarDataEvento(eventoCriarRequestDto.data());

        Usuario organizador = buscarUsuarioNoBanco(eventoCriarRequestDto.organizadorId())
            .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado" + ".!!"));

        LocalCerimonia localCerimonia = buscarLocalCerimoniaNoBanco(eventoCriarRequestDto.localCerimoniaId()).orElseThrow(
            () -> new RegistroNaoEncontradoException("Local de" + " cerimônia não encontrado"));

        var eventoBanco = buscarEventoNoBanco(eventoCriarRequestDto.data(), organizador,
            localCerimonia);

        if (eventoBanco.isPresent()) {
            Evento eventoExistente = eventoBanco.get();
            Evento eventoAtualizado = atualizarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador,
                localCerimonia, eventoExistente);
            return eventoRepository.save(eventoAtualizado);
        } else {
            Evento evento = criarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador, localCerimonia);
            return eventoRepository.save(evento);
        }
    }

    public Evento atualizarEvento(Long id, EventoCriarRequestDto eventoCriarRequestDto) {

        var eventoBancoId = eventoRepository.findByIdAndStatusNot(id, EnumStatusEvento.EXCLUIDO)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        validarDataEvento(eventoCriarRequestDto.data());

        Usuario organizador = buscarUsuarioNoBanco(eventoCriarRequestDto.organizadorId()).orElseThrow(
            () -> new RegistroNaoEncontradoException("Usuário não encontrado.!!"));

        LocalCerimonia localCerimonia = buscarLocalCerimoniaNoBanco(eventoCriarRequestDto.localCerimoniaId()).orElseThrow(
            () -> new RegistroNaoEncontradoException("Local de cerimônia não encontrado"));

        var eventoBancoChek = buscarEventoNoBanco(eventoCriarRequestDto.data(), organizador,
            localCerimonia);

        if (eventoBancoChek.isPresent() && !(eventoBancoChek.get().getId() == eventoBancoId.getId())) {
            throw new ValidacoesRegraNegocioException(
                "Já existe um evento cadastrado com essa data, organizador e local de cerimônia.");
        } else {
            Evento eventoAtualizado = atualizarEventoBaseadoNoTipo(eventoCriarRequestDto, organizador,
                localCerimonia, eventoBancoId);
            return eventoRepository.save(eventoAtualizado);
        }
    }

    // Metodo Factory para criar a instância do evento
    private Evento criarEventoBaseadoNoTipo(EventoCriarRequestDto dto, Usuario organizador,
        LocalCerimonia localCerimonia) {

        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());

        return switch (tipoEvento) {
            case FORMATURA -> new EventoFormatura(dto, organizador, localCerimonia);
            case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia);
            default -> throw new IllegalArgumentException("Tipo de evento inválido: " + dto.tipoEvento());
        };
    }

    // Metodo Factory para atualizar um evento
    private Evento atualizarEventoBaseadoNoTipo(EventoCriarRequestDto dto, Usuario organizador,
        LocalCerimonia localCerimonia, Evento eventoBanco) {
        var tipoEvento = EnumTipoEvento.fromCodigo(dto.tipoEvento());

        return switch (tipoEvento) {
            case FORMATURA -> new EventoFormatura(dto, organizador, localCerimonia).atualizarEventoFromDTO(
                (EventoFormatura) eventoBanco, dto, organizador, localCerimonia);
            case PALESTRA -> new EventoPalestra(dto, organizador, localCerimonia).atualizarEventoFromDTO(
                (EventoPalestra)  eventoBanco, dto, organizador, localCerimonia);
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
            throw new ValidacoesRegraNegocioException(
                "Este Evento já está com sua capacidade máxima.!!!");
        }

        if (evento.isPast()) {
            throw new ValidacoesRegraNegocioException("Não é possível se inscrever. Evento já realizado");
        }
        evento.setInscritos(evento.getInscritos() + 1);
        return eventoRepository.save(evento);
    }

    private void validarDataEvento(String dataString) {
        LocalDateTime dataEvento = LocalDate.parse(dataString,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
        LocalDateTime hoje = LocalDate.now().atStartOfDay();

        if (dataEvento.isBefore(hoje)) {
            throw new ValidacoesRegraNegocioException("Não é possível criar evento com data retroativa");
        }
    }

    private Optional<Evento> buscarEventoNoBanco(String data, Usuario organizador,
        LocalCerimonia localCerimonia) {
        var dataEvento = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .atStartOfDay();

        var eventoBanco = eventoRepository.findByDataAndOrganizadorAndLocalCerimoniaAndStatusNotOrderById(
            dataEvento, organizador, localCerimonia, EnumStatusEvento.EXCLUIDO);
        // Retorna o primeiro, se houver duplicidade, pode lançar exceção ou tratar conforme regra
        return eventoBanco.isEmpty() ? Optional.empty() : Optional.of(eventoBanco.get(0));
    }


    private Optional<Usuario> buscarUsuarioNoBanco(Long id) {
        return usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO);
    }

    private Optional<LocalCerimonia> buscarLocalCerimoniaNoBanco(Long id) {
        return localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO);

    }

}
