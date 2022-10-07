package com.attrecto.academy.java.courseapp.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attrecto.academy.java.courseapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByName(String name);
}