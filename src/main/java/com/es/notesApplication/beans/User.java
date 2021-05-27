package com.es.notesApplication.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@JsonIgnoreProperties(value={ "password", "notes" }, allowSetters= true)
public class User {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(unique = true)
	@Email(message = "Email is invalid")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, max = 20, message = "Password should be between 8 - 20 Characters")
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
