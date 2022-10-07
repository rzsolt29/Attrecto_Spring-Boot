package com.attrecto.academy.java.courseapp.model.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserDto extends CreateUserDto {
	@Schema(description = "Id's of the user courses")
	private Set<Integer> courses = new HashSet<>();
	public Set<Integer> getCourses() {
		return courses;
	}
	public void setCourses(Set<Integer> courses) {
		this.courses = courses;
	}
}