package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.localCerimonia.LocalCerimoniaResponseDto;
import com.eventos.senac.apieventos_senac.application.dto.usuario.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.LocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.repository.LocalCerimoniaRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusLocalCerimonia;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalCerimoniaService {

    @Autowired
    public LocalCerimoniaRepository localCerimoniaRepository;


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


}
