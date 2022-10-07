package com.attrecto.academy.java.courseapp.model.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDto extends MinimalUserDto {
	@Schema(description = "Id's of the user courses")
	private Set<MinimalCourseDto> courses = new HashSet<>();

	public Set<MinimalCourseDto> getCourses() {
		return courses;
	}
	public void setCourses(Set<MinimalCourseDto> courses) {
		this.courses = courses;
	}
}