package com.assignment.registerservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(unique = true)
	private int id;

	@NotBlank
	@NotEmpty
	private String password;

	@Email
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User(int id, @NotBlank @NotEmpty String password, @Email String email) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;

	}

	public User() {

	}

}
