package com.es.notesApplication.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "notes")
@NoArgsConstructor
public class Note {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String title;
	private String body;
	private String color;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"notes", "password"})
	private User user;

	public Note(String title, String body, String color, User user) {
		super();
		this.title = title;
		this.body = body;
		this.color = color;
		this.user = user;
	}
}
