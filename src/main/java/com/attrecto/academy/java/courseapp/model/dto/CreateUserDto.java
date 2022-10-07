package com.attrecto.academy.java.courseapp.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.attrecto.academy.java.courseapp.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class CreateUserDto {
	@NotBlank
	@Schema(description = "Name of the user", example = "user")
	private String name;
	@NotBlank
	@Schema(description = "Email of the user", example = "user@gmail.com")
	private String email;
	@NotBlank
	@Schema(description = "Password of the user", example = "password")
	private String password;
	@NotNull
	@Schema(description = "Role of the user", example = "USER")
	private Role role;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}