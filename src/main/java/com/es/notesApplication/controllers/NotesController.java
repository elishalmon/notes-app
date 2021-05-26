package com.es.notesApplication.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.es.notesApplication.beans.Note;
import com.es.notesApplication.jwt.JwtService;
import com.es.notesApplication.services.NoteService;


@RestController
@RequestMapping("notes")
public class NotesController {
	
	@Autowired 
	NoteService service;
	
	@CrossOrigin
	@PostMapping("addNote")
	public ResponseEntity<?> addNote(@Valid @RequestBody Note note, @RequestHeader(value = "Authorization", required = false) String auth) {
		try {
			JwtService.decodeJWT(auth);
			Note newNote = service.addNote(note);
			return new ResponseEntity<Note>(newNote, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@DeleteMapping("deleteNote/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String auth) {
		try {
			JwtService.decodeJWT(auth);
			service.deleteNote(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@PutMapping("updateNote")
	public ResponseEntity<?> updateNote(@Valid @RequestBody Note note, @RequestHeader(value = "Authorization", required = false) String auth) {
		try {
			JwtService.decodeJWT(auth);
			Note updatedNote = service.updateNote(note);
			return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST); 
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
