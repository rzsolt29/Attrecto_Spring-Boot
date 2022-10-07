package com.attrecto.academy.java.courseapp.service.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import com.attrecto.academy.java.courseapp.mapper.CourseMapper;
import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.MinimalCourseDto;
import com.attrecto.academy.java.courseapp.persistence.CourseRepository;
import com.attrecto.academy.java.courseapp.persistence.UserRepository;

@Component
public class ServiceUtil {
	private CourseRepository courseRepository;
	private UserRepository userRepository;
	
	public ServiceUtil(final CourseRepository courseRepository, final UserRepository userRepository) {
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}
	
	public Course findCourseById(int id) {
		Optional<Course> course = courseRepository.findById(id);
		if(course.isEmpty()) {
			throw new NotFoundException(String.format("Course cannot be found with id: %s", id));
		}
		return course.get();
	}

	public User findUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new NotFoundException(String.format("User cannot be found with id: %s", id));
		}
		return user.get();
	}

	public List<MinimalCourseDto> listUserCourses(User user) {
		return user.getCourses().stream().map(CourseMapper::mapToMinimal).collect(Collectors.toList());
	}
}