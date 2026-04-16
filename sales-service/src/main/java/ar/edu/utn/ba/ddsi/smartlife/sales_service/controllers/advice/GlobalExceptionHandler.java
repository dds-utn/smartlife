package ar.edu.utn.ba.ddsi.smartlife.sales_service.controllers.advice;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.error.ErrorResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.ConflictException;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
		return build(HttpStatus.NOT_FOUND, "not_found", ex.getMessage());
	}

	@ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex) {
		return build(HttpStatus.BAD_REQUEST, "bad_request", ex.getMessage());
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
		return build(HttpStatus.CONFLICT, "conflict", ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
		return build(HttpStatus.INTERNAL_SERVER_ERROR, "internal_error", "Ocurrió un error interno");
	}

	private ResponseEntity<ErrorResponse> build(HttpStatus status, String error, String message) {
		return ResponseEntity.status(status)
			.body(new ErrorResponse(error, message, Instant.now()));
	}
}
