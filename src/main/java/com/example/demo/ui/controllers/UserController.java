package com.example.demo.ui.controllers;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ui.excepciones.UserServiceException;
import com.example.demo.ui.model.request.UpdateUserRequestDetailModel;
import com.example.demo.ui.model.request.UserRequestDetailModel;
import com.example.demo.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

	//------------------------
	//			G E T
	//------------------------
	
	// Mapa de Datos en memoria
	Map<String, UserRest> usuarios;

	// Paginacion de resultados http://localhost:8080/users?page=1&limit=50
	@GetMapping
	public String getUsers(			
			// @RequestParam(value="page", required = false) <-- Esta es otra forma de hacerlo opcional,
			// pero da error con primitivos porque no inicializa variables numericas,
			// logicas o de fecha pero si el tipo de datos es String este se inicializa como nulo
			@RequestParam(value = "page", defaultValue = "1") int pagina,
			@RequestParam(value = "limit", defaultValue = "50") int limite,
			@RequestParam(value = "sort", defaultValue = "desc") String sort) {
		return "get usuarios ha sido invocado en la pagina = " + pagina + " y limite = " + limite + " sort = " + sort;
	}

	// Especificar un Id a extraer http://localhost:8080/users/1
	// Tambien especificamos que el metodo es capaz de generar tanto JSON como XML en la salida
	@GetMapping(
			path = "/{userId}", 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	// ResponseEntity sirve para combinar la respuesta con codigo de respuesta, cuerpo y encabezado personalizado
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {

		// Esta es la forma de personalizar las excepciones del servicio
		if(true) throw new UserServiceException("Este es una excepcion invocada para pruebas");
		
		if (usuarios.containsKey(userId)) {
			// Si existe el usuario lo retornamos
			return new ResponseEntity<>(usuarios.get(userId), HttpStatus.OK);
		} else {
			// De lo contrario se retorna un NO_CONTENT que es lo mismo que un body vacio
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	//------------------------
	//	      P O S T
	//------------------------

	// Consume especifica que la data puede llegar tanto en XML como en JSON
	// a la vez que tambien puede devolver ambos formatos
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	// RequestBody lee el JSON que trae el request en el body para insertar al nuevo
	// usuario
	public ResponseEntity<UserRest> postUser(@Valid @RequestBody UserRequestDetailModel userDetails) {

		UserRest retorno = new UserRest();
		retorno.setEmail(userDetails.getEmail());
		retorno.setFirstName(userDetails.getFirstName());
		retorno.setLastName(userDetails.getLastName());

		// Generando un nuevo ID para el nuevo usuario
		String usuarioId = UUID.randomUUID().toString();
		retorno.setUserId(usuarioId);

		// Inicializando la base de datos en memoria si esta null
		if (usuarios == null) usuarios = new HashMap<>();
		
		// Para la base de datos en memoria, agregamos al usuario
		usuarios.put(usuarioId, retorno);

		return new ResponseEntity<UserRest>(retorno, HttpStatus.OK);
	}

	//------------------------
	//			P U T
	//------------------------

	
	@PutMapping(
			path = "/{userId}", 
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserRest> putUser(@PathVariable String userId, @Valid @RequestBody UpdateUserRequestDetailModel userDetails) {
		
		// Buscando el id de usuario
		if (usuarios.containsKey(userId)) {
			UserRest storedUserDetails = usuarios.get(userId);
			// Seteando los cambios
			storedUserDetails.setFirstName(userDetails.getFirstName());
			storedUserDetails.setLastName(userDetails.getLastName());
			
			// Actualizando el record en memoria
			usuarios.put(userId, storedUserDetails);
			
			return new ResponseEntity<>(usuarios.get(userId), HttpStatus.OK);
		} else {
			return ResponseEntity.noContent().build();
		}		
	}

	//------------------------
	//	    D E L E T E
	//------------------------

	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Void> delUser(@PathVariable String userId) {
		usuarios.remove(userId);
		return ResponseEntity.noContent().build();
	}
}