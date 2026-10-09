package com.tecsup.historiaclinica.usuarios;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Map;
@RestControllerAdvice(assignableTypes=UsuariosController.class)
public class GestionExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> invalid(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("mensaje",e.getMessage()));}
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> duplicate(){return ResponseEntity.status(409).body(Map.of("mensaje","Registro duplicado o relacion invalida"));}
}
