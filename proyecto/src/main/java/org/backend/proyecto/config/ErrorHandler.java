package org.backend.proyecto.config;

import lombok.extern.slf4j.Slf4j;
import org.backend.proyecto.dto.ErrorDTO;
import org.backend.proyecto.exception.RuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    // Maneja las excepciones de validación durante la creación o actualización de productos y pedidos.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> validationError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors(); // Convierte los errores de validación en un objeto ErrorDTO y devuelve una respuesta HTTP 400 con algunos detalles sobre los errores de validación.
        List<String> errors = fieldErrors.stream().map(x -> x.getDefaultMessage()).toList();


        ErrorDTO errorDTO = new ErrorDTO(
                "ERR_VALIDATION",
                "Error de validación en los datos de entrada",
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeError(RuntimeException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                ex.getCode(),
                ex.getMessage(),
                ex.getDetails()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> unknownError(Exception ex) {
        log.error(ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "ERR_UNKOWN",
                "Ocurrió un error inesperado...",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}

/*
Maneja las excepciones, especialmente con la validación de datos.
En este caso, se encarga de convertir los errores de validación en un objeto 'ErrorDTO'
y devuelve una respuesta 'HTTP 400' con detalles sobre los errores de validación.
*/
