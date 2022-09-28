package com.attrecto.academy.java.courseapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.model.dto.CreateCourseDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalUserDto;
import com.attrecto.academy.java.courseapp.persistence.CourseRepository;
import com.attrecto.academy.java.courseapp.service.util.ServiceUtil;

@Service
public class CourseService {
	private CourseRepository courseRepository;
	private ServiceUtil serviceUtil;

	public CourseService(CourseRepository courseRepository, ServiceUtil serviceUtil) {
		this.courseRepository = courseRepository;
		this.serviceUtil = serviceUtil;
	}

	public List<CourseDto> listAllCourses() {
		List<Course> courses = courseRepository.findAll();
		return courses.stream().map(course -> {
			CourseDto courseDto = new CourseDto();
			courseDto.setStudents(course.getStudents().stream().map(student -> {
				MinimalUserDto minimalUserDto = new MinimalUserDto();
				minimalUserDto.setId(student.getId());
				minimalUserDto.setName(student.getName());
				minimalUserDto.setEmail(student.getEmail());
				return minimalUserDto;
			}).collect(Collectors.toList()));

			return courseDto;
		}).collect(Collectors.toList());
	}

	public CourseDto getCourseById(int id) {
		Course course = serviceUtil.findCourseById(id);
		CourseDto courseDto = new CourseDto();
		courseDto.setStudents(course.getStudents().stream().map(user -> {
			MinimalUserDto minimalUserDto = new MinimalUserDto();
			minimalUserDto.setId(user.getId());
			minimalUserDto.setName(user.getName());
			minimalUserDto.setEmail(user.getEmail());
			return minimalUserDto;
		}).collect(Collectors.toList()));
		return courseDto;
	}

	public CourseDto createCourse(CreateCourseDto createCourseDto) {
		Course course = new Course();
		course.setTitle(createCourseDto.getTitle());
		course.setDescription(createCourseDto.getDescription());
		course.setUrl(createCourseDto.getUrl());
		course.setAuthorId(serviceUtil.findUserById(createCourseDto.getAuthorId()).getId());
		course.setStudents(createCourseDto.getStudentIds().stream()
				.map(studentId -> serviceUtil.findUserById(studentId)).collect(Collectors.toList()));

		course = courseRepository.save(course);
		
		CourseDto courseDto = new CourseDto();
		courseDto.setId(course.getId());
		courseDto.setTitle(course.getTitle());
		courseDto.setDescription(course.getDescription());
		courseDto.setUrl(course.getUrl());
		courseDto.setAuthorId(course.getAuthorId());
		courseDto.setStudents(course.getStudents().stream().map(student -> {
			MinimalUserDto minimalUserDto = new MinimalUserDto();
			minimalUserDto.setId(student.getId());
			minimalUserDto.setName(student.getName());
			minimalUserDto.setEmail(student.getEmail());
			return minimalUserDto;
		}).collect(Collectors.toList()));
		
		return courseDto;
	}

	public CourseDto updateCourse(final int id, CreateCourseDto updateCourseDto) {
		Course course = serviceUtil.findCourseById(id);
		course.setDescription(updateCourseDto.getDescription());
		course.setTitle(updateCourseDto.getTitle());
		course.setUrl(updateCourseDto.getUrl());

		course = courseRepository.save(course);
		
		CourseDto courseDto = new CourseDto();
		courseDto.setId(course.getId());
		courseDto.setTitle(course.getTitle());
		courseDto.setDescription(course.getDescription());
		courseDto.setUrl(course.getUrl());
		courseDto.setAuthorId(course.getAuthorId());
		courseDto.setStudents(course.getStudents().stream().map(student -> {
			MinimalUserDto minimalUserDto = new MinimalUserDto();
			minimalUserDto.setId(student.getId());
			minimalUserDto.setName(student.getName());
			minimalUserDto.setEmail(student.getEmail());
			return minimalUserDto;
		}).collect(Collectors.toList()));
		
		return courseDto;
	}

	public void deleteCourse(int id) {
		serviceUtil.findCourseById(id);

		courseRepository.deleteById(id);
	}
}