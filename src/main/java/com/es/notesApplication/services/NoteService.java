package com.es.notesApplication.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.notesApplication.beans.Note;
import com.es.notesApplication.beans.User;
import com.es.notesApplication.repositories.NoteRepository;
import com.es.notesApplication.repositories.UserRepository;

@Service
public class NoteService {

		@Autowired
		NoteRepository repo;
		
		@Autowired
		UserRepository userRepo;
		
		public List<Note> addNote(Note note) throws Exception {
			repo.save(note);
			List<Note> list = getNotesByUserId(note.getUser().getId());
			return list;
		}
		
		public List<Note> getNotes() {
			Iterable<Note> itrbl = this.repo.findAll();
			List<Note> list = new ArrayList<Note>();
			for (Note note : itrbl) {
				list.add(note);
			}
			return list;
		}
		
		public Note getNote(int id) throws Exception {
			Optional<Note> op =  this.repo.findById(id);
			if(op.isEmpty()) {
				throw new Exception("Note with id " + id + " does not exist");
			}
			return op.get();
		}
		
		public List<Note> getNotesByUserId(int id) throws Exception {
			List<Note> list = this.repo.findByUserId(id);			
			if(list.size() == 0) {
				throw new Exception("No notes yet for user with id " + id);
			}
			return list;
		}
		
		public List<Note> deleteNote(int id) throws Exception {
			Note note = this.getNote(id);
			User user = note.getUser();
			user.removeNote(note);
			userRepo.save(user);
			List<Note> list = getNotesByUserId(user.getId());
			return list;
		}
		
		
		public List<Note> updateNote(Note note) throws Exception {
			repo.save(note);
			List<Note> list = getNotesByUserId(note.getUser().getId());
			return list;
		}
		
		/*
		 
		 public Note addNote(Note note) {
			return this.repo.save(note);
		}
		  
		  
		 * public Note updateNote(Note note) {
			return this.repo.save(note);
		}*/
}
