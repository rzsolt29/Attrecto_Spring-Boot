package com.attrecto.academy.java.courseapp.model.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public class MinimalCourseDto {
	@NotBlank
	@Schema(description = "Id of the course", example = "1")
	private int id;
	@NotBlank
	@Schema(description = "Title of the course", example = "Spring boot")
	private String title;
	@NotBlank
	@Schema(description = "Description of the course", example = "Java fundamentals and Spring Boot")
	private String description;
	@NotBlank
	@Schema(description = "URL for the course", example = "https://attrecto.com/academy/course/java")	
	private String url;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
}