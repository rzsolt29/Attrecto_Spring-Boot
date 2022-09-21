package com.attrecto.academy.java.courseapp.model.dto;

import java.util.List;

public class CourseDto {
	private List<MinimalUserDto> students;

	public List<MinimalUserDto> getStudents() {
		return students;
	}
	public void setStudents(List<MinimalUserDto> students) {
		this.students = students;
	}
}