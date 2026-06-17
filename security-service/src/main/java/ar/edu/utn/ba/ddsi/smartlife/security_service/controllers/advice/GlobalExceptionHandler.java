package ar.edu.utn.ba.ddsi.smartlife.security_service.controllers.advice;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.error.ErrorResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.HogarEnPeligroException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger;

    public GlobalExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        logger.log(ex);
        return build(HttpStatus.NOT_FOUND, "not_found", ex.getMessage());
    }

    @ExceptionHandler(HogarEnPeligroException.class)
    public ResponseEntity<ErrorResponse> handleHogarEnPeligro(HogarEnPeligroException ex) {
        logger.log(ex);
        return build(HttpStatus.FORBIDDEN, "hogar_en_peligro", ex.getMessage());
    }

    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex) {
        logger.log(ex);
        return build(HttpStatus.BAD_REQUEST, "bad_request", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        logger.log(ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "internal_error", "Ocurrió un error interno");
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status)
            .body(new ErrorResponse(error, message, Instant.now()));
    }
}
