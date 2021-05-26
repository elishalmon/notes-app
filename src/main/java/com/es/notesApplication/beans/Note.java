package com.es.notesApplication.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


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
	
	@NotBlank(message = "Title is mandatory")
	private String title;
	
	@NotBlank(message = "Body is mandatory")
	private String body;
	
	@NotBlank(message = "Color is mandatory")
	private String color;
	
	@NotNull(message = "isRead is mandatory")
	private boolean isRead;
	
	@Min(value = 1, message = "Priority should be between 1 to 5")
    @Max(value = 5, message = "Priority should be between 1 to 5")
	private int priority;
	
	@NotBlank(message = "Icon is mandatory")
	private String icon;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"notes", "password"})
	private User user;

	public Note(int id, String title, String body, String color, boolean isRead, int priority, User user) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.color = color;
		this.isRead = isRead;
		this.priority = priority;
		this.user = user;
	}
}
