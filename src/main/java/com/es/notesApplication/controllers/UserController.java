package com.es.notesApplication.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.es.notesApplication.beans.Note;
import com.es.notesApplication.beans.User;
import com.es.notesApplication.jwt.JwtService;
import com.es.notesApplication.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	
	@Autowired
	UserService service;
	
	@CrossOrigin
	@PostMapping("addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
		try {
			user = this.service.addUser(user);
			return new ResponseEntity<User>(user , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.FORBIDDEN);
		}
	}
	
	
	@CrossOrigin
	@GetMapping("getNotes")
	public ResponseEntity<?> getNotes(@RequestParam int id, @RequestHeader(value = "Authorization", required = false) String auth) {
		try {
			JwtService.decodeJWT(auth);
			List<Note> list = this.service.getNotes(id);
			return new ResponseEntity<List<Note>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("get notes exception");
			return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@PostMapping("getUserLogin")
	public ResponseEntity<?> getUserLogin(@RequestBody Map<String, String> details){
		try {
			Map<String, String> response = service.findByEmailAndPassword(details.get("email"), details.get("password"));
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.FORBIDDEN);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		System.out.println(errors);
		return errors;
	}
}
