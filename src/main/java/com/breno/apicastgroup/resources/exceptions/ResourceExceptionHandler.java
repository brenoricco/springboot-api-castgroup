package com.breno.apicastgroup.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.breno.apicastgroup.services.exception.InvalidAuthenticationException;
import com.breno.apicastgroup.services.exception.InvalidInputDataException;
import com.breno.apicastgroup.services.exception.ObjectNotFoundException;
import com.breno.apicastgroup.services.exception.OrderDeclinedException;
import com.breno.apicastgroup.services.exception.SendMailException;



@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InvalidInputDataException.class)
	public ResponseEntity<StandardError> objectNotFound(InvalidInputDataException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Dados de entrada inválidos", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(OrderDeclinedException.class)
	public ResponseEntity<StandardError> objectNotFound(OrderDeclinedException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Pedido negado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(InvalidAuthenticationException.class)
	public ResponseEntity<StandardError> objectNotFound(InvalidAuthenticationException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Autenticação não autorizada", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(SendMailException.class)
	public ResponseEntity<StandardError> objectNotFound(SendMailException e, HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Autenticação não autorizada", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
