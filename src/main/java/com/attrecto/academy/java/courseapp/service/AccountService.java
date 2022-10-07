package com.attrecto.academy.java.courseapp.service;

import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.model.dto.LoginDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalUserDto;

@Service
public class AccountService {
	public String generateJwtToken(final LoginDto loginDto) {
		return "";
	}
	
	public MinimalUserDto getLoggedUser() {
		MinimalUserDto minimalUserDto = new MinimalUserDto();
		minimalUserDto.setId(1);
		minimalUserDto.setName("mockUser");
		minimalUserDto.setEmail("mockUser@email.com");
		return minimalUserDto;
	}
}