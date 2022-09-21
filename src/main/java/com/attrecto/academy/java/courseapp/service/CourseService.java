package com.attrecto.academy.java.courseapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.model.dto.CreateCourseDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalUserDto;

@Service
public class CourseService {

	private MinimalUserDto firstUser;
	private MinimalUserDto secondUser;
	private CourseDto firstCourse;
	private CourseDto secondCourse;
	
	//TODO:Fiktív kurzusok és userek létrehozása
	public CourseService() {
		firstUser = new MinimalUserDto();
		firstUser.setId(1);
		firstUser.setName("firstUser");
		firstUser.setEmail("firstUser@attrecto.com");
		
		firstCourse = new CourseDto();
		firstCourse.setStudents(new ArrayList<>(Arrays.asList(firstUser)));

		secondUser = new MinimalUserDto();
		secondUser.setId(2);
		secondUser.setName("secondUser");
		secondUser.setEmail("secondUser@attrecto.com");
		
		secondCourse = new CourseDto();
		secondCourse.setStudents(new ArrayList<>(Arrays.asList(secondUser)));
	}

	//TODO: Teszt célból a valós kurzusok helyett egyenlőre két fiktív kurzust adunk vissza
	public List<CourseDto> listAllCourses() {
		return new ArrayList<>(Arrays.asList(firstCourse, secondCourse));
	}

	//TODO: Teszt célból a valós kurzus helyett egyenlőre egy fiktív kurzust adunk vissza
	public CourseDto getCourseById(Integer id) {
		return firstCourse;
	}

	//TODO: Teszt célból a valós kurzus helyett egyenlőre egy fiktív kurzust "hozunk létre" és térünk vele vissza
	public CourseDto createCourse(CreateCourseDto createCourseDto) {
		CourseDto newCourseDto = new CourseDto();
		newCourseDto.setStudents(createCourseDto.getStudentIds().stream().map(id -> {
			MinimalUserDto minimalUserDto = new MinimalUserDto();
			minimalUserDto.setId(id);
			minimalUserDto.setName("user" + id);
			minimalUserDto.setEmail(String.format("user%semail@attrecto.com", id));
			return minimalUserDto;
		}).collect(Collectors.toList()));

		return newCourseDto;
	}

	//TODO: Teszt célból a valós kurzus helyett egyenlőre egy fiktív kurzust módosítunk és térünk vele vissza
	public CourseDto updateCourse(Integer id, CreateCourseDto createCourseDto) {
		CourseDto updatedCourseDto = new CourseDto();
		updatedCourseDto.setStudents(new ArrayList<>(Arrays.asList(firstUser, secondUser)));
		return updatedCourseDto;
	}

	//TODO: Teszt célból a valós kurzus törlése helyett nem csinálunk egyenlőre semmit
	public void deleteCourse(Integer id) {
	}
}