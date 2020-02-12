package com.nelioalves.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound (ObjectNotFoundException ex, HttpServletRequest request){
		
		StandardError  err =  new StandardError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrity (DataIntegrityException ex, HttpServletRequest request){
		
		StandardError  err =  new StandardError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> dataIntegrity (MethodArgumentNotValidException ex, HttpServletRequest request){
		
		ValidationError  err =  new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", System.currentTimeMillis());
		for (FieldError x : ex.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
		
	}
}
