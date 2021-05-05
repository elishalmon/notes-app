package com.es.notesApplication.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.es.notesApplication.beans.Note;
import com.es.notesApplication.beans.User;
import com.es.notesApplication.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	
	@Autowired
	UserService service;
	
	@CrossOrigin
	@PostMapping("addUser")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			user = this.service.addUser(user);
			return new ResponseEntity<User>(user , HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.FORBIDDEN);
		}
		
	}
	
	@CrossOrigin
	@GetMapping("getNotes")
	public ResponseEntity<?> getNotes(@RequestParam int id) {
		try {
			List<Note> list = this.service.getNotes(id);
			return new ResponseEntity<List<Note>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Note>>(new ArrayList<Note>(), HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin
	@GetMapping("getByName")
	public ResponseEntity<?> getByName(@RequestParam String name){
		User user= this.service.findByName(name);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("getUserLogin")
	public ResponseEntity<?> getUserLogin(@RequestBody Map<String, String> details){
		try {
			User user = this.service.findByEmailAndPassword(details.get("email"), details.get("password"));
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Exception>(e, HttpStatus.FORBIDDEN);
		}
	}
}
