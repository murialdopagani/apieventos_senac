package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.repository.LocalCerimoniaRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalCerimoniaService {

    @Autowired
    public LocalCerimoniaRepository localCerimoniaRepository;

    public LocalCerimoniaResponseDto salvarLocalCerimonia(LocalCerimoniaCriarRequestDto localCerimoniaDto) {
        var localCerimoniaDB = localCerimoniaRepository.findByCnpj_CnpjAndStatusNot(String.valueOf(localCerimoniaDto.cnpj()),
            EnumStatusLocalCerimonia.EXCLUIDO).orElse(new LocalCerimonia(localCerimoniaDto));

        if (localCerimoniaDB.getId() != null) {
            localCerimoniaDB = localCerimoniaDB.atualizarLocalCerimoniaFromDto(localCerimoniaDB, localCerimoniaDto);
        }
        return new LocalCerimoniaResponseDto(localCerimoniaRepository.save(localCerimoniaDB));
    }


    public LocalCerimoniaResponseDto buscarPorId(Long id) {
        return localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO)
            .stream()
            .map(LocalCerimoniaResponseDto::new)
            .findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Local de cerimonias não encontrado"));
    }

    public LocalCerimonia buscarPorIdObj(Long id) {
        return localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO)
            .stream()
            .findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Local de cerimonias não encontrado"));
    }


    public List<LocalCerimoniaResponseDto> listarTodos() {
        return localCerimoniaRepository.findAll()
            .stream()
            .map(LocalCerimoniaResponseDto::new)
            .collect(Collectors.toList());
    }

    public boolean excluirLocalCerimonia(Long id) {
        var local = localCerimoniaRepository.findById(id)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Local de Cerimonias não encontrado"));
        alterarStatusLocalCerimonia(local, EnumStatusLocalCerimonia.EXCLUIDO);
        return true;
    }


    public boolean desbloquearLocalCerimonia(Long id) {
        var local = localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Local de Cerimonias não encontrado"));
        alterarStatusLocalCerimonia(local, EnumStatusLocalCerimonia.ATIVO);
        return true;
    }

    public boolean bloquearLocalCerimonia(Long id) {
        var local = localCerimoniaRepository.findByIdAndStatusNot(id, EnumStatusLocalCerimonia.EXCLUIDO)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Local de Cerimonias não encontrado"));
        alterarStatusLocalCerimonia(local, EnumStatusLocalCerimonia.BLOQUEADO);
        return true;
    }

    private void alterarStatusLocalCerimonia(LocalCerimonia localCerimonia, EnumStatusLocalCerimonia statusLocalCerimonia) {
        localCerimonia.setStatus(statusLocalCerimonia);
        localCerimoniaRepository.save(localCerimonia);
    }

}
