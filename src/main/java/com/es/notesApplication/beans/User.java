package com.es.notesApplication.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String email;
	private String name;
	private String password;
	
	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.EAGER,
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	@ToString.Exclude
	private List<Note> notes = new ArrayList<Note>();
	
	public User(String email, String name, String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	public void removeNote(Note note) {
		this.notes.remove(note);
	}
}
