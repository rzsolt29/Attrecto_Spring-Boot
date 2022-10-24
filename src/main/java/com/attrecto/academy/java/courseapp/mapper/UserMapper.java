package com.attrecto.academy.java.courseapp.mapper;

import java.util.stream.Collectors;

import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.MinimalUserDto;
import com.attrecto.academy.java.courseapp.model.dto.UserDto;

public class UserMapper {

	public static MinimalUserDto  mapToMinimal(final User user) {
		MinimalUserDto minimalUserDto = new MinimalUserDto();
		minimalUserDto.setId(user.getId());
		minimalUserDto.setName(user.getName());
		minimalUserDto.setEmail(user.getEmail());
		return minimalUserDto;
	}

	public static UserDto map(final User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole());
		userDto.setCourses(user.getCourses().stream().map(CourseMapper::mapToMinimal).collect(Collectors.toSet()));
		
		return userDto;
	}
	
	public static User map(final MinimalUserDto userDto) {
		return null;
	}

	public static User map(final UserDto userDto) {
		return null;
	}
}