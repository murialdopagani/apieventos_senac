package com.eventos.senac.apieventos_senac.presentation;

import com.eventos.senac.apieventos_senac.application.dto.error.ErroResponseDto;
import com.eventos.senac.apieventos_senac.exception.RegistroNaoEncontradoException;
import com.eventos.senac.apieventos_senac.exception.ValidacoesRegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponseDto> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.NOT_FOUND,
            ex.getMessage() != null ? ex.getMessage() : "Recurso não encontrado", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponseDto> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.CONFLICT, "Violação de integridade: " + ex.getMostSpecificCause()
            .getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDto> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String mensagens = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(f -> f.getField() + ": " + f.getDefaultMessage())
            .collect(Collectors.joining(", "));

        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.BAD_REQUEST, mensagens, request.getRequestURI());
        return ResponseEntity.badRequest()
            .body(erro);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroResponseDto> handleIllegalArgument(IllegalStateException ex, HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest()
            .body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponseDto> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest()
            .body(erro);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDto> handleRegistroNaoEncontrado(RegistroNaoEncontradoException ex,
        HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.NOT_FOUND,
            ex.getMessage() != null ? ex.getMessage() : "Registro não encontrado", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDto> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Erro em {} {}: {}", request.getMethod(), request.getRequestURI(), ex.toString(), ex);
        var erro = ErroResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado, tente novamente mais tarde",
            request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(erro);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErroResponseDto> handleDateTimeParseException(DateTimeParseException ex, HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.BAD_REQUEST,
            "Formato de data/hora inválido.! A data deve ser no formato (dd/MM/yyyy HH:mm) " + ex.getParsedString(),
            request.getRequestURI());
        return ResponseEntity.badRequest()
            .body(erro);
    }

    @ExceptionHandler(ValidacoesRegraNegocioException.class)
    public ResponseEntity<ErroResponseDto> handleRegraNegocio(ValidacoesRegraNegocioException ex, HttpServletRequest request) {
        ErroResponseDto erro = ErroResponseDto.of(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.badRequest()
            .body(erro);
    }

}
