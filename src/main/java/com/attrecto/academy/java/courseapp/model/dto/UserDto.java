package com.attrecto.academy.java.courseapp.model.dto;

import java.util.List;

public class UserDto {
	private int id;
	private String email;
	private String name;
	private List<MinimalCourseDto> courses;
	
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
	public List<MinimalCourseDto> getCourses() {
		return courses;
	}
	public void setCourses(List<MinimalCourseDto> courses) {
		this.courses = courses;
	}
}