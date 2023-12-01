package org.backend.proyecto.exception;

import java.util.LinkedList;
import java.util.List;
import org.backend.proyecto.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductoExceptionAdvice {

    // Maneja las excepciones de validación durante la creación o actualización de productos.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors(); // Convierte los errores de validación en un objeto ErrorDTO y devuelve una respuesta HTTP 400 con algunos detalles sobre los errores de validación.
        List<String> errors = new LinkedList<>();

        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }

        ErrorDTO errorDTO = new ErrorDTO("ERR_VALIDATION", "Error de validación en los datos de entrada", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}

/*
Maneja las excepciones, especialmente con la validación de datos.
En este caso, se encarga de convertir los errores de validación en un objeto 'ErrorDTO'
y devuelve una respuesta 'HTTP 400' con detalles sobre los errores de validación. 
*/