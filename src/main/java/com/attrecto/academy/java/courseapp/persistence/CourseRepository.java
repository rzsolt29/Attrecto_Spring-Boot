package com.attrecto.academy.java.courseapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attrecto.academy.java.courseapp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}