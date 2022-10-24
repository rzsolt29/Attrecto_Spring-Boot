package com.attrecto.academy.java.courseapp.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class CourseDto extends MinimalCourseDto {
	@NotBlank
	@Schema(description = "The course author", example = "user")	
	private MinimalUserDto author;
	@Schema(description = "List of the course users", example = "user, admin")	
	private Set<MinimalUserDto> students = new HashSet<>();

	
	public MinimalUserDto getAuthor() {
		return author;
	}
	public void setAuthor(MinimalUserDto author) {
		this.author = author;
	}
	public Set<MinimalUserDto> getStudents() {
		return students;
	}
	public void setStudents(Set<MinimalUserDto> students) {
		this.students = students;
	}
}