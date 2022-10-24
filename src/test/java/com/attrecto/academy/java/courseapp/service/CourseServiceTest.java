package com.attrecto.academy.java.courseapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.attrecto.academy.java.courseapp.model.Course;
import com.attrecto.academy.java.courseapp.model.Role;
import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.CourseDto;
import com.attrecto.academy.java.courseapp.persistence.CourseRepository;
import com.attrecto.academy.java.courseapp.service.util.ServiceUtil;
import com.google.common.collect.Sets;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
	@Mock
	private CourseRepository courseRepository;

	@Mock
	private ServiceUtil ServiceUtil;

	@InjectMocks
	private CourseService courseService;
	
	private Course firstTestCourse;
	private Course secondTestCourse;
	
	public CourseServiceTest() {
		User firstTestUser = new User();
		firstTestUser.setId(1);
		firstTestUser.setName("First test user");
		firstTestUser.setEmail("firsttestuser@test.com");
		firstTestUser.setPassword("passw0rd");
		firstTestUser.setRole(Role.ADMIN);

		User secondTestUser = new User();
		secondTestUser.setId(2);
		secondTestUser.setName("Second test user");
		secondTestUser.setEmail("secondtestuser@test.com");
		secondTestUser.setPassword("passw0rd");
		secondTestUser.setRole(Role.USER);

		firstTestCourse = new Course();
		firstTestCourse.setId(1);
		firstTestCourse.setAuthor(firstTestUser);
		firstTestCourse.setDescription("First course");
		firstTestCourse.setTitle("Test course");
		firstTestCourse.setUrl("https://randomurl.com");
		firstTestCourse.setStudents(Sets.newHashSet(firstTestUser, secondTestUser));
		
		secondTestCourse = new Course();
		secondTestCourse.setId(2);
		secondTestCourse.setAuthor(firstTestUser);
		secondTestCourse.setDescription("Second course");
		secondTestCourse.setTitle("Test course");
		secondTestCourse.setUrl("https://randomurl.com");
		secondTestCourse.setStudents(Sets.newHashSet(secondTestUser));
	}

	@Test
	public void testAllCoursesAreListed() {
		when(courseRepository.findAll()).thenReturn(Arrays.asList(firstTestCourse, secondTestCourse));
		List<CourseDto> courses = courseService.listAllCourses();
		
		assertEquals(courses.size(), 2);
		CourseDto firstCourse = courses.get(0);
		CourseDto secondCourse = courses.get(1);
		
		assertEquals(firstCourse.getId(), firstTestCourse.getId());
		assertEquals(firstCourse.getAuthor().getId(), firstTestCourse.getAuthor().getId());
		assertEquals(firstCourse.getDescription(), firstTestCourse.getDescription());
		assertEquals(firstCourse.getTitle(), firstTestCourse.getTitle());
		assertEquals(firstCourse.getUrl(), firstTestCourse.getUrl());
		assertEquals(firstCourse.getStudents().size(), 2);

		assertEquals(secondCourse.getId(), secondTestCourse.getId());
		assertEquals(secondCourse.getAuthor().getId(), secondTestCourse.getAuthor().getId());
		assertEquals(secondCourse.getDescription(), secondTestCourse.getDescription());
		assertEquals(secondCourse.getTitle(), secondTestCourse.getTitle());
		assertEquals(secondCourse.getUrl(), secondTestCourse.getUrl());
		assertEquals(secondCourse.getStudents().size(), 1);
	}
}