package com.attrecto.academy.java.courseapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.model.dto.CreateCourseDto;
import com.attrecto.academy.java.courseapp.model.dto.CreateUserDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalUserDto;

@Service
public class UserService {

	private MinimalUserDto firstUser;
	private MinimalUserDto secondUser;
	private CourseDto firstCourse;
	private CourseDto secondCourse;
	
	//TODO:Fiktív kurzusok és userek létrehozása
	public UserService() {
		firstUser = new MinimalUserDto();
		firstUser.setId(1);
		firstUser.setName("firstUser");
		firstUser.setEmail("firstUser@attrecto.com");
		
//		firstCourse = new CourseDto();
//		firstCourse.setStudents(new ArrayList<>(Arrays.asList(firstUser)));

		secondUser = new MinimalUserDto();
		secondUser.setId(2);
		secondUser.setName("secondUser");
		secondUser.setEmail("secondUser@attrecto.com");
		
//		secondCourse = new CourseDto();
//		secondCourse.setStudents(new ArrayList<>(Arrays.asList(secondUser)));
	}

	//TODO: Teszt célból a valós felhasználók helyett egyenlőre két fiktív kurzust adunk vissza
	public List<MinimalUserDto> listAllUsers() {
		return new ArrayList<>(Arrays.asList(firstUser, secondUser));
	}

	//TODO: Teszt célból a valós felhasználó helyett egyenlőre egy fiktív kurzust adunk vissza
	public MinimalUserDto getUserById(Integer id) {
		return firstUser;
	}

//	//TODO: Teszt célból a valós kurzus helyett egyenlőre egy fiktív kurzust "hozunk létre" és térünk vele vissza
//	public MinimalUserDto createCourse(CreateUserDto createUserDto) {
//		UserDto newUserDto = new UserDto();
//		newUserDto.setStudents(createUserDto.getStudentIds().stream().map(id -> {
//			MinimalUserDto minimalUserDto = new MinimalUserDto();
//			minimalUserDto.setId(id);
//			minimalUserDto.setName("user" + id);
//			minimalUserDto.setEmail(String.format("user%semail@attrecto.com", id));
//			return minimalUserDto;
//		}).collect(Collectors.toList()));
//
//		return newUserDto;
//	}

//	//TODO: Teszt célból a valós felhasználó helyett egyenlőre egy fiktív felhasználót módosítunk és térünk vele vissza
//	public MinimalUserDto updateUser(Integer id, CreateUserDto createUserDto) {
//		CourseDto updatedUserDto = new UserDto();
//		updatedUserDto.setStudents(new ArrayList<>(Arrays.asList(firstUser, secondUser)));
//		return updatedUserDto;
//	}

	//TODO: Teszt célból a valós felhasználó törlése helyett nem csinálunk egyenlőre semmit
	public void deleteUser(Integer id) {
	}
}
