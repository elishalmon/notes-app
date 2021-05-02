package com.es.notesApplication.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.notesApplication.beans.Note;
import com.es.notesApplication.beans.User;
import com.es.notesApplication.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repo;
	
	public User addUser(User user) throws Exception {
		List<User> list = repo.findByEmail(user.getEmail());
		if(list.size() > 0) {
			throw new Exception("This email is already exist");
		}
		return this.repo.save(user);
	}
	
	public User getUserById(int id) throws Exception {
		Optional<User> op = this.repo.findById(id);
		if(op.isEmpty()) {
			throw new Exception("User with id " + id + " does not exist");
		}
		return op.get();
	}
	
	public void deleteUser(int id) throws Exception {
		User user = this.getUserById(id);
		this.repo.delete(user);
	}
	
	public List<Note> getNotes(int id) throws Exception {
		User user = this.getUserById(id);
		List<Note> list =  user.getNotes();
		if(list.size() == 0) {
			throw new Exception("User with id " + id + " does not have notes yet");
		}
		return list;
	}
	
	public User getUser(int id) throws Exception {
		Optional<User> u = repo.findById(id);
		if(u.isEmpty()) { // user not found
			throw new Exception();
		}
		return u.get();
	}
	
	public User findByName(String name) {
		Optional<User> op = this.repo.findByName(name);
		return op.get();
	}
	
	public User findByEmailAndPassword(String email, String password) throws Exception {
		Optional<User> op = this.repo.findByEmailAndPassword(email, password);
		if(op.isEmpty()) {
			throw new Exception("User not exist");
		}
		return op.get();
	}
}
