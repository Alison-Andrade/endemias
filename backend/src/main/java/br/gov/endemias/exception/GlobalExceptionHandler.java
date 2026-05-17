package br.gov.endemias.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(MethodArgumentNotValidException e) {
        List<String> messagens = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        ErroResponse response = new ErroResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            messagens
        );

        return ResponseEntity.badRequest().body(response);
    }

    public ResponseEntity<ErroResponse> tratarRegraNegocio(RegraNegocioException e) {
        ErroResponse response = new ErroResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de regra de negócio",
            List.of(e.getMessage())
        );

        return ResponseEntity.badRequest().body(response);
    }

}
