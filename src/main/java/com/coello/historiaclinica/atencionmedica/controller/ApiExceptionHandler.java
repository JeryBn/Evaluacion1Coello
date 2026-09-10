package com.coello.historiaclinica.atencionmedica.controller;

import com.coello.historiaclinica.atencionmedica.service.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, Object> body = error(HttpStatus.BAD_REQUEST, "Datos invalidos en la solicitud");
        Map<String, String> campos = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> campos.put(error.getField(), error.getDefaultMessage()));
        body.put("campos", campos);
        return ResponseEntity.badRequest().body(body);
    }

    private Map<String, Object> error(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("fecha", LocalDateTime.now());
        body.put("estado", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("mensaje", message);
        return body;
    }
}
