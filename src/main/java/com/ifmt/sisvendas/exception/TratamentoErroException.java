package com.ifmt.sisvendas.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe usada para representar erros tratados pela API.
 *
 * Permite padronizar mensagens de erro retornadas ao cliente.
 */
@RestControllerAdvice
public class TratamentoErroException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErro(Exception erro) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "dataHora", LocalDateTime.now(),
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "erro", "Erro interno no servidor",
                        "mensagem", erro.getMessage()
                ));
    }
}
