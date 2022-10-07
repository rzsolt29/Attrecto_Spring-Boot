package com.attrecto.academy.java.courseapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.mapper.UserMapper;
import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.Role;
import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.CreateUserDto;
import com.attrecto.academy.java.courseapp.model.dto.UpdateUserDto;
import com.attrecto.academy.java.courseapp.model.dto.UserDto;
import com.attrecto.academy.java.courseapp.persistence.CourseRepository;
import com.attrecto.academy.java.courseapp.persistence.UserRepository;
import com.attrecto.academy.java.courseapp.service.util.ServiceUtil;

@Service
public class UserService {
	private UserRepository userRepository;
	private CourseRepository courseRepository;
	private ServiceUtil serviceUtil;

	@Autowired
	public UserService(final UserRepository userRepository, final CourseRepository courseRepository, final ServiceUtil serviceUtil) {
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.serviceUtil = serviceUtil;
	}

	public List<UserDto> listUsers() {
		return userRepository.findAll().stream().map(UserMapper::map).collect(Collectors.toList());
	}

	public UserDto getUserById(final int id) {
		return UserMapper.map(serviceUtil.findUserById(id));
	}

	public UserDto updateUser(int id, UpdateUserDto updateUserDto) {
		Set<Course> courses = updateUserDto.getCourses().stream()
				.map(courseId -> serviceUtil.findCourseById(courseId)).collect(Collectors.toSet());

		final User user = serviceUtil.findUserById(id);
		user.setName(updateUserDto.getName());
		user.setEmail(updateUserDto.getEmail());
		user.setPassword(updateUserDto.getPassword());
		user.setRole(updateUserDto.getRole());
		user.setCourses(courses);
		final User updatedUser = userRepository.save(user);
		
		courseRepository.findAll().forEach(course -> {
			if(courses.contains(course)) {
				course.getStudents().add(user);				
			} else {
				course.getStudents().remove(user);
			}
			
			courseRepository.save(course);
		});
		
		return UserMapper.map(updatedUser);
	}

	public UserDto createUser(CreateUserDto createUserDto) {
		User user = new User();
		user.setName(createUserDto.getName());
		user.setPassword(createUserDto.getPassword());
		user.setEmail(createUserDto.getEmail());
		user.setRole(Role.USER);
		user.setCourses(new HashSet<>());
		user = userRepository.save(user);

		return UserMapper.map(user);
	}

	public void deleteUser(final int id) {
		serviceUtil.findUserById(id);
		
		userRepository.deleteById(id);
	}
}