package com.attrecto.academy.java.courseapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attrecto.academy.java.courseapp.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}