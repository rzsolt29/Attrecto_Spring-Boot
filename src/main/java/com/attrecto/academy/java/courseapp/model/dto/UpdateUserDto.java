package com.attrecto.academy.java.courseapp.model.dto;

import java.util.List;

public class UpdateUserDto extends CreateUserDto {
	private List<Integer> courses;
	public List<Integer> getCourses() {
		return courses;
	}
	public void setCourses(List<Integer> courses) {
		this.courses = courses;
	}
}