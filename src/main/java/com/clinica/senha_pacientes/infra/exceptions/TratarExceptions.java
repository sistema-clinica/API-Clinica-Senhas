package com.clinica.senha_pacientes.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarExceptions {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity tratarExcecao500(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErroMessage(ex.getMessage()));
    }

    private record ErroMessage(String message) {}

}
