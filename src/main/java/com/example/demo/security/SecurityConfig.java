package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		.cors(cors -> cors.disable())              // CORS 방지
    		.csrf(csrf -> csrf.disable())              // CSRF 방지
    		.authorizeHttpRequests(auth -> auth
    			.requestMatchers("/member/update").permitAll() // 로그인 없이 접근 허용
    			.anyRequest().permitAll()              // 나머지도 임시로 허용
    		)
    		.formLogin(form -> form.disable())      //기본 로그인 페이지 제거

    		.logout(logout -> logout
            .logoutUrl("/member/logout")
            .logoutSuccessUrl("/member/login")
            .permitAll()
        );
    	
    	return http.build();
    }
}