package com.es.notesApplication.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.es.notesApplication.beans.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	public List<User> findByEmail(String email);
}
