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

	
	//TODO:Fiktív kurzusok és userek létrehozása
	public UserService() {
		firstUser = new MinimalUserDto();
		firstUser.setId(1);
		firstUser.setName("firstUser");
		firstUser.setEmail("firstUser@attrecto.com");
		
		secondUser = new MinimalUserDto();
		secondUser.setId(2);
		secondUser.setName("secondUser");
		secondUser.setEmail("secondUser@attrecto.com");
		
	}

	//TODO: Teszt célból a valós felhasználók helyett egyenlőre két fiktív kurzust adunk vissza
	public List<MinimalUserDto> listAllUsers() {
		return new ArrayList<>(Arrays.asList(firstUser, secondUser));
	}

	//TODO: Teszt célból a valós felhasználó helyett egyenlőre egy fiktív kurzust adunk vissza
	public MinimalUserDto getUserById(Integer id) {
		return firstUser;
	}

	//TODO: Teszt célból a valós felhasználó helyett egyenlőre egy fiktív kurzust "hozunk létre" és térünk vele vissza
	public MinimalUserDto createUser(CreateUserDto createUserDto) {
		MinimalUserDto newUserDto = new MinimalUserDto();
		newUserDto.setId(4);
		newUserDto.setName("New Name");
		newUserDto.setEmail("newuseremail@attrecto.com");
		return newUserDto;
	}

	//TODO: Teszt célból a valós felhasználó helyett egyenlőre egy fiktív felhasználót módosítunk és térünk vele vissza
	public MinimalUserDto updateUser(Integer id, CreateUserDto createUserDto) {
		MinimalUserDto updatedUserDto = new MinimalUserDto();
		updatedUserDto.setId(4);
		updatedUserDto.setName("New Name");
		updatedUserDto.setEmail("newuseremail@attrecto.com");

		return updatedUserDto;
	}

	//TODO: Teszt célból a valós felhasználó törlése helyett nem csinálunk egyenlőre semmit
	public void deleteUser(Integer id) {
	}
}
