package com.hospital.config;

import org.springframework.security.config.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration {
	

	@Bean
	SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth
		.requestMatchers("**").permitAll()
		.requestMatchers("/admin/**").hasAnyRole("ADMIN")
		.requestMatchers("/search/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/nurse/allNurses/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/manager/allManagers/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/worker/all**/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/**/add**/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/**/update**/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/**/delete**/**").hasAnyRole("ADMIN")
		.requestMatchers("/**/choose**/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/patient/getPatientDepartments/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/patient/bookNewDepartment/**").hasAnyRole("ADMIN", "NURSE")
		.requestMatchers("/patient/get**/**").hasAnyRole("ADMIN", "NURSE")
		.anyRequest().authenticated()
		)
		.headers(headers->headers.frameOptions(frame->frame.sameOrigin()))
		.formLogin(Customizer.withDefaults())
		.logout(logout->logout.logoutSuccessUrl("/")
				.invalidateHttpSession(true).permitAll());
		return http.build();
	}
	
	
}
