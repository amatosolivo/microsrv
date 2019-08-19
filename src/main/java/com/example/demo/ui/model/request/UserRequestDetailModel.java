package com.example.demo.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Clase para procesar los request de POST de usuarios
public class UserRequestDetailModel {
	// Para mas referencias sobre validacion consultar
	// https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single
	
	@NotNull(message = "First Name cannot be null")
	@Size(min = 2, message = "First Name must have more than 2 characters")
	private String firstName;
	
	@NotNull(message = "Last Name cannot be null")
	@Size(min = 2, message = "Last Name must have more than 2 characters")
	private String lastName;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "Formato de correo incorrecto")
	private String email;
	
	@NotNull
	@Size(min = 8, max = 16, message = "Password must be >= 8 and <= 16")
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
