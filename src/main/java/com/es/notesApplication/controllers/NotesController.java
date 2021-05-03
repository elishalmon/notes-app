package com.es.notesApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.notesApplication.beans.Note;
import com.es.notesApplication.services.NoteService;


@RestController
@RequestMapping("notes")
public class NotesController {
	
	
	@Autowired 
	NoteService service;
	
	@CrossOrigin
	@PostMapping("addNote")
	public ResponseEntity<Note> addNote(@RequestBody Note note) {
		note = this.service.addNote(note);
		return new ResponseEntity<Note>(note, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("{id}") 
	public ResponseEntity<?> displayNote(@PathVariable int id) {
		try {
			Note note = this.service.getNote(id);
			return new ResponseEntity<Note>(note, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@DeleteMapping("deleteNote/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable int id) {
		try {
			this.service.deleteNote(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@PutMapping("updateNote")
	public ResponseEntity<Note> updateNote(@RequestBody Note note) {
		note = this.service.updateNote(note);
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}
	
	
}
