package com.es.notesApplication.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.es.notesApplication.beans.Note;

public interface NoteRepository extends CrudRepository<Note, Integer> {
	public List<Note> findByUserId(int id);
}
