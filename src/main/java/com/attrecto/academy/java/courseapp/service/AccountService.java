package com.attrecto.academy.java.courseapp.service;

import org.springframework.stereotype.Service;

import com.attrecto.academy.java.courseapp.model.dto.LoginDto;
import com.attrecto.academy.java.courseapp.model.dto.MinimalUserDto;

@Service
public class AccountService {

	public String generateJwtToken(LoginDto loginDto) {
		// TODO Auto-generated method stub
		return "test";
	}

	public MinimalUserDto getLoggedUser() {
		MinimalUserDto dto=new MinimalUserDto();
		dto.setId(1);
		dto.setName("user");
		dto.setEmail("usertestemail@attrecto.com");
		
		return dto;
	}

}
