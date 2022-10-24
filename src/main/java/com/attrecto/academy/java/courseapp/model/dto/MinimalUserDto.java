package com.attrecto.academy.java.courseapp.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.attrecto.academy.java.courseapp.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;

public class MinimalUserDto {
	@NotNull
	@Schema(description = "Id of the user", example = "1")	
	private int id;
	@NotBlank
	@Schema(description = "Name of the user", example = "user")	
	private String name;
	@NotBlank
	@Schema(description = "Email of the user", example = "user@gmail.com")
	private String email;
	private Role role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}