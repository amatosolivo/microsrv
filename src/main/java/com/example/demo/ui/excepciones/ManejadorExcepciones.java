package com.example.demo.ui.excepciones;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.ui.model.response.ErrorMessage;

@ControllerAdvice
public class ManejadorExcepciones extends ResponseEntityExceptionHandler {
	
	// Excepcion generalizada
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleAnyExceptionEntity(Exception ex, WebRequest request) {
		
		String descripcion = ex.getLocalizedMessage();		
		if (descripcion == null) descripcion = ex.toString();		
		ErrorMessage mensajeError = new ErrorMessage(new Date(), descripcion);		
		return new ResponseEntity<>(mensajeError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		//ex representa el stack trace completo del error que estamos devolviendo en el response
	}
	
	// Manejador de multiples Excepciones
	@ExceptionHandler(value = {NullPointerException.class, UserServiceException.class})
	public ResponseEntity<Object> handleSpecificExceptionEntity(Exception ex, WebRequest request) {
		
		String descripcion = ex.getLocalizedMessage();		
		if (descripcion == null) descripcion = ex.toString();		
		ErrorMessage mensajeError = new ErrorMessage(new Date(), descripcion);		
		return new ResponseEntity<>(mensajeError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
}