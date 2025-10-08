package com.eventos.senac.apieventos_senac.application.services;

import com.eventos.senac.apieventos_senac.application.dto.requestDto.UsuarioCriarRequestDto;
import com.eventos.senac.apieventos_senac.application.dto.responseDto.UsuarioResponseDto;
import com.eventos.senac.apieventos_senac.domain.entity.Usuario;
import com.eventos.senac.apieventos_senac.domain.repository.UsuarioRepository;
import com.eventos.senac.apieventos_senac.domain.valueobjects.Cpf;
import com.eventos.senac.apieventos_senac.domain.valueobjects.EnumStatusUsuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepository.findAllByStatusNot(EnumStatusUsuario.EXCLUIDO).stream().map(UsuarioResponseDto::new)
            .collect(Collectors.toList());
    }

    public UsuarioResponseDto buscarPorId(Long id) {
        return usuarioRepository.findByIdAndStatusNot(id, EnumStatusUsuario.EXCLUIDO)
            .stream()
            .map(UsuarioResponseDto::new)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UsuarioResponseDto salvarUsuario(UsuarioCriarRequestDto usuarioRequestDto) throws Exception {
        var usuarioBanco = usuarioRepository.findByCpf_CpfAndStatusNot(String.valueOf(new Cpf(usuarioRequestDto.cpf())),
            EnumStatusUsuario.EXCLUIDO).orElse(new Usuario(usuarioRequestDto));

        if (usuarioBanco.getId() != null) {
            usuarioBanco = usuarioBanco.atualizarUsuarioFromDTO(usuarioBanco, usuarioRequestDto);
        }

        usuarioRepository.save(usuarioBanco);
        UsuarioResponseDto usuarioResponseDto = UsuarioResponseDto.fromUsuario(usuarioBanco);

        return new UsuarioResponseDto(usuarioBanco);
    }
}
