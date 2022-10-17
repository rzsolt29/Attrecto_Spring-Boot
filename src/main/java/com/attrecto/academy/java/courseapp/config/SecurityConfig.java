package com.attrecto.academy.java.courseapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    	this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
        .csrf().disable()
        .httpBasic().disable()
        .cors()
        .and()
        .headers().frameOptions().disable()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "**").permitAll()
        .and()
        .authorizeRequests().antMatchers("/").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/openapi-ui/**").permitAll()
        .antMatchers("/api/account/login/**").permitAll()
        .antMatchers("/api/users/**").authenticated()
        .antMatchers("/api/courses/**").authenticated()
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
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

    //dummy implementation to suppress some default Spring Security configuration
    @Bean
    public UserDetailsService dummyUserDetailsService() {
        return username -> null;
    }
}