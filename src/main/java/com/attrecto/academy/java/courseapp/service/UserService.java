package com.attrecto.academy.java.courseapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.Role;
import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.CreateUserDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalCourseDto;
import com.attrecto.academy.java.courseapp.model.dto.UpdateUserDto;
import com.attrecto.academy.java.courseapp.model.dto.UserDto;
import com.attrecto.academy.java.courseapp.persistence.UserRepository;
import com.attrecto.academy.java.courseapp.service.util.ServiceUtil;

@Service
public class UserService {
	private UserRepository userRepository;
	private ServiceUtil serviceUtil;

	@Autowired
	public UserService(final UserRepository userRepository, final ServiceUtil serviceUtil) {
		this.userRepository = userRepository;
		this.serviceUtil = serviceUtil;
	}

	public List<UserDto> listUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> {
			List<MinimalCourseDto> minimalCoursesDto = serviceUtil.listUserCourses(user);

			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setCourses(minimalCoursesDto);
			return userDto;
		}).collect(Collectors.toList());

		return userDtos;
	}

	public UserDto getUserById(final int id) {
		User user = serviceUtil.findUserById(id);

		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setCourses(serviceUtil.listUserCourses(user));

		return userDto;
	}

	public UserDto updateUser(int id, UpdateUserDto updateUserDto) {
		List<Course> courses = updateUserDto.getCourses().stream()
				.map(courseId -> serviceUtil.findCourseById(courseId)).collect(Collectors.toList());

		User user = serviceUtil.findUserById(id);
		user.setName(updateUserDto.getName());
		user.setEmail(updateUserDto.getEmail());
		user.setPassword(updateUserDto.getPassword());
		user.setRole(updateUserDto.getRole());
		user.setCourses(courses);
		userRepository.save(user);

		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setName(updateUserDto.getName());
		userDto.setEmail(updateUserDto.getEmail());
		userDto.setCourses(user.getCourses().stream().map(course -> {
			MinimalCourseDto minimalCourseDto = new MinimalCourseDto();
			minimalCourseDto.setId(course.getId());
			minimalCourseDto.setTitle(course.getTitle());
			minimalCourseDto.setUrl(course.getUrl());
			minimalCourseDto.setDescription(course.getDescription());
			return minimalCourseDto;
		}).collect(Collectors.toList()));

		return userDto;
	}

	public UserDto createUser(CreateUserDto createUserDto) {
		User user = new User();
		user.setName(createUserDto.getName());
		user.setPassword(createUserDto.getPassword());
		user.setEmail(createUserDto.getEmail());
		user.setRole(Role.USER.name());
		user.setCourses(new ArrayList<>());

		user = userRepository.save(user);

		final UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());

		return userDto;
	}

	public void deleteUser(final int id) {
		serviceUtil.findUserById(id);
		
		userRepository.deleteById(id);
	}
}