package com.attrecto.academy.java.courseapp.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.model.dto.CreateCourseDto;
import com.attrecto.academy.java.courseapp.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course API")
public class CourseController {
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all courses" ,security = {@SecurityRequirement(name = "token")})
    public List<CourseDto> getAllCourse() {
    	return courseService.listAllCourses();
    }
    
    @GetMapping(value= "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a course by id" ,security = {@SecurityRequirement(name = "token")})
    public CourseDto getCourseById(@PathVariable final Integer id) {
    	return courseService.getCourseById(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create a new course" ,security = {@SecurityRequirement(name = "token")})
    public CourseDto createCourse(@Valid @RequestBody CreateCourseDto createCourseDto) {
    	return courseService.createCourse(createCourseDto);
    }
    
    @PutMapping(value= "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing course" ,security = {@SecurityRequirement(name = "token")})
    public CourseDto updateCourse(@PathVariable final Integer id, @Valid @RequestBody CreateCourseDto createCourseDto) {
    	return courseService.updateCourse(id, createCourseDto);
    }    
    
    @DeleteMapping(value= "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing course" ,security = {@SecurityRequirement(name = "token")})
    public void deleteCourse(@PathVariable final Integer id) {
    	courseService.deleteCourse(id);
    }        
}