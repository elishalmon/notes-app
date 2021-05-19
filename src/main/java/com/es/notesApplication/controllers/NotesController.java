package com.es.notesApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> addNote(@RequestBody Note note, @RequestHeader(value = "Authorization", required = false) String auth) {
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
	public ResponseEntity<?> updateNote(@RequestBody Note note, @RequestHeader(value = "Authorization", required = false) String auth) {
		try {
			JwtService.decodeJWT(auth);
			Note updatedNote = service.updateNote(note);
			return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.NOT_FOUND);
		}
	}
}
