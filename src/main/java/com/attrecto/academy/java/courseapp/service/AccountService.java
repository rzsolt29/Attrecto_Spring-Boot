package com.attrecto.academy.java.courseapp.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jose4j.keys.HmacKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.attrecto.academy.java.courseapp.config.JwtAuthenticationFilter;
import com.attrecto.academy.java.courseapp.mapper.CourseMapper;
import com.attrecto.academy.java.courseapp.model.Role;
import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.LoginDto;
import com.attrecto.academy.java.courseapp.model.dto.TokenDto;
import com.attrecto.academy.java.courseapp.model.dto.UserDto;
import com.attrecto.academy.java.courseapp.persistence.UserRepository;
import com.google.common.hash.Hashing;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AccountService {
	private UserRepository userRepository;

	public AccountService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public TokenDto generateJwtToken(final LoginDto loginDto) {
		String userName = loginDto.getUserName();
		final Optional<User> maybeUser = userRepository.findByName(userName);

		if (maybeUser.isEmpty()) {
			throw new NotFoundException(String.format("User with name '%s' have been not found.", userName));
		}

		final User user = maybeUser.get();
		String password = loginDto.getPassword();

		final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
		if (!user.getPassword().equals(hashedPassword)) {
			throw new RuntimeException("Password is incorrect!");
		}

		String jwtToken = Jwts.builder().claim("name", user.getName()).setSubject(user.getName())
				.setId(user.getId().toString()).setIssuedAt(new Date()).setAudience(JwtAuthenticationFilter.AUDIENCE)
				.setIssuer(JwtAuthenticationFilter.ISSUER)
				.setExpiration(Date.from(LocalDateTime.now().plusSeconds(JwtAuthenticationFilter.MAX_TIME_TO_LIVE_SEC)
						.toInstant(ZoneOffset.UTC)))
				.signWith(SignatureAlgorithm.HS256,
						new HmacKey(JwtAuthenticationFilter.SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
				.compact();

		TokenDto tokenDto = new TokenDto();
		tokenDto.setToken(jwtToken);
		return tokenDto;
	}

	public UserDto getLoggedUser() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			User user = userRepository.findByName(auth.getName()).get();

			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			userDto.setEmail(user.getEmail());
			userDto.setRole(user.getRole());
			userDto.setCourses(user.getCourses().stream().map(course -> CourseMapper.mapToMinimal(course))
					.collect(Collectors.toSet()));

			return userDto;
		} catch (Exception ex) {
			UserDto user = new UserDto();
			user.setRole(Role.USER);
			return user;
		}
	}
}