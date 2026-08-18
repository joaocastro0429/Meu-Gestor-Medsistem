package br.com.meugestormedsistem.web.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.*;

/** Converte as exceções mais comuns em respostas de erro padronizadas. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail tratarStatus(ResponseStatusException ex, HttpServletRequest request) {
        String message = ex.getReason() == null ? "Operação não realizada" : ex.getReason();
        return criar(ex.getStatusCode(), message, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarValidacao(MethodArgumentNotValidException ex,
                                          HttpServletRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fields.putIfAbsent(error.getField(), error.getDefaultMessage()));

        ProblemDetail problem = criar(HttpStatus.BAD_REQUEST, "Existem campos inválidos", request);
        problem.setProperty("fields", fields);
        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail tratarConflitoDetail(HttpServletRequest request) {
        return criar(HttpStatus.CONFLICT, "A operação viola uma regra dos dados", request);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class})
    public ProblemDetail tratarRequisicaoInvalidaDetail(HttpServletRequest request) {
        return criar(HttpStatus.BAD_REQUEST, "JSON ou parâmetro inválido", request);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ProblemDetail tratarErroDoSpring(ErrorResponseException ex,
                                             HttpServletRequest request) {
        String message = ex.getBody().getDetail();
        if (message == null) message = "Requisição não realizada";
        return criar(ex.getStatusCode(), message, request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail tratarErroInesperado(HttpServletRequest request) {
        return criar(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno", request);
    }

    private ProblemDetail criar(HttpStatusCode status, String message, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, message);
        problem.setTitle(HttpStatus.valueOf(status.value()).getReasonPhrase());
        problem.setInstance(URI.create(request.getRequestURI()));
        return problem;
    }
}
