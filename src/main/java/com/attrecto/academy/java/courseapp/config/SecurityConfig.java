package com.attrecto.academy.java.courseapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.attrecto.academy.java.courseapp.persistence.UserRepository;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private UserRepository userRepository;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.httpBasic().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and()
				.cors()
				.and()
				.headers().frameOptions().disable()
				.and()
				.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "**").permitAll()
				.antMatchers("/api/account/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/h2-console/**", "/openapi-ui/**", "/api/account/login");
	}

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> logFilter() {
	    FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new JwtAuthenticationFilter(userRepository));
	    registrationBean.addUrlPatterns("/api/courses/**", "/api/users/**");
	    return registrationBean;
	}
	
	@Bean
	public CorsFilter corsFilter() {
		final CorsConfiguration corsConfig = new CorsConfiguration();
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		corsConfig.setAllowCredentials(false);
		corsConfig.addAllowedOrigin("*");
		corsConfig.addAllowedHeader("*");
		corsConfig.addAllowedMethod("*");
		corsConfig.addExposedHeader("");
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsFilter(source);
	}

	// dummy implementation to suppress some default Spring Security configuration
	@Bean
	public UserDetailsService dummyUserDetailsService() {
		return username -> null;
	}
}