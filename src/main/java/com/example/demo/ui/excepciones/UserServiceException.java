package com.example.demo.ui.excepciones;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = -1736636798723093604L;

	public UserServiceException(String mensaje) {
		super(mensaje);
	}
}
