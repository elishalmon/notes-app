package com.es.notesApplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.es.notesApplication.beans.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	public List<User> findByEmail(String email);
	public Optional<User> findByEmailAndPassword(String name, String password);
}
