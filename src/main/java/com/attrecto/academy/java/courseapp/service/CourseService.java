package com.attrecto.academy.java.courseapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.mapper.CourseMapper;
import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.model.dto.CreateCourseDto;
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
		return courses.stream().map(CourseMapper::map).collect(Collectors.toList());
	}

	public CourseDto getCourseById(int id) {
		return CourseMapper.map(serviceUtil.findCourseById(id));
	}

	public CourseDto createCourse(CreateCourseDto createCourseDto) {
		final Course course = new Course();
		course.setTitle(createCourseDto.getTitle());
		course.setDescription(createCourseDto.getDescription());
		course.setUrl(createCourseDto.getUrl());
		course.setStartingDate(createCourseDto.getStartingDate());
		course.setEndingDate(createCourseDto.getEndingDate());
		course.setAuthor(serviceUtil.findUserById(createCourseDto.getAuthorId()));
		course.setStudents(createCourseDto.getStudentIds().stream().map(userId -> serviceUtil.findUserById(userId))
				.collect(Collectors.toSet()));

		return CourseMapper.map(courseRepository.save(course));
	}

	public CourseDto updateCourse(final int id, CreateCourseDto updateCourseDto) {
		Course course = serviceUtil.findCourseById(id);
		course.setDescription(updateCourseDto.getDescription());
		course.setTitle(updateCourseDto.getTitle());
		course.setUrl(updateCourseDto.getUrl());
		course.setStartingDate(updateCourseDto.getStartingDate());
		course.setEndingDate(updateCourseDto.getEndingDate());
		course.setAuthor(serviceUtil.findUserById(updateCourseDto.getAuthorId()));
		course.setStudents(updateCourseDto.getStudentIds().stream().map(userId -> serviceUtil.findUserById(userId))
				.collect(Collectors.toSet()));

		return CourseMapper.map(courseRepository.save(course));
	}

	public void deleteCourse(int id) {
		serviceUtil.findCourseById(id);

		courseRepository.deleteById(id);
	}
}