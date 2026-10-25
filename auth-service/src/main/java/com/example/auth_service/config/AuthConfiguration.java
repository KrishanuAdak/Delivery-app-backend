package com.example.auth_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfiguration {

	// @Lazy
	// private final UserDetailsImpl userDetails;

	// public AuthConfiguration(UserDetailsImpl userDetails) {
	// 	this.userDetails = userDetails;
	// }

	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain secureHttps(HttpSecurity http) throws Exception {
		try {
			http.csrf().disable().authorizeHttpRequests(auth -> auth.requestMatchers("/auth-service/**","/actuator/**").permitAll()
					.anyRequest().authenticated())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
					//.exceptionHandling(ex -> ex.accessDeniedHandler(new AuthAccessDeniedHandler()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return http.build();
	}

	

}
