package br.com.zup.cataliza.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TaxNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleTaxNotFound(TaxNotFoundException exception) {
		Map<String, String> error = new HashMap();
		error.put("error", exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Map<String, String>> handleUnAuthorizedException(UnauthorizedException exception) {
		Map<String, String> error = new HashMap<>();
		error.put("error", exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(ForbiddenException.class)
	public  ResponseEntity<Map<String, String>> handleForbiddenException(ForbiddenException exception){
		Map<String, String> error = new HashMap<>();
		error.put("error", exception.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
}
