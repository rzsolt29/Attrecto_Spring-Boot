package com.attrecto.academy.java.courseapp.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class CreateCourseDto {
	@Max(100)
	@NotBlank
	@Schema(description = "Title of the course", example = "Java course")
	private String title;
	@Max(1000)
	@NotBlank
	@Schema(description = "Description of the course", example = "Java fundamentals and Spring Boot")	
	private String description;
	@NotBlank
	@Schema(description = "URL for the course", example = "https://attrecto.com/academy/course/java")	
	private String url;
	@NotNull
	@Schema(description = "Id of the of the course author", example = "1")	
	private Integer authorId;
	private Set<Integer> studentIds = new HashSet<>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Set<Integer> getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(Set<Integer> studentIds) {
		this.studentIds = studentIds;
	}
}
