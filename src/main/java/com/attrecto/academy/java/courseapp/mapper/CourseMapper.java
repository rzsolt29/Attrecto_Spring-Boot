package com.attrecto.academy.java.courseapp.mapper;

import java.util.stream.Collectors;

import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalCourseDto;

public class CourseMapper {

	public static MinimalCourseDto mapToMinimal(final Course course) {
		MinimalCourseDto minimalCourseDto = new MinimalCourseDto();
		minimalCourseDto.setId(course.getId());
		minimalCourseDto.setDescription(course.getDescription());
		minimalCourseDto.setTitle(course.getTitle());
		minimalCourseDto.setUrl(course.getUrl());
		
		return minimalCourseDto;
	}

	public static CourseDto map(final Course course) {
		CourseDto courseDto = new CourseDto();
		courseDto.setId(course.getId());
		courseDto.setDescription(course.getDescription());
		courseDto.setTitle(course.getTitle());
		courseDto.setUrl(course.getUrl());
		courseDto.setAuthorId(course.getAuthor().getId());
		courseDto.setStudents(course.getStudents().stream().map(UserMapper::map).collect(Collectors.toSet()));
		
		return courseDto;
	}
	
	public static Course map(final MinimalCourseDto courseDto) {
		return null;
	}

	public static Course map(final CourseDto courseDto) {
		return null;
	}
	
}