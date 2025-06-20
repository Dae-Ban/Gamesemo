package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 기본 로그인페이지로 이동하는 거, 강제로 없애기 !!
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		.cors(cors -> cors.disable())              // CORS 방지
    		.csrf(csrf -> csrf.disable())              // CSRF 방지
    		.authorizeHttpRequests(auth -> auth
    			.requestMatchers("/member/update","/", "/member/login", "/css/**", "/js/**", "/images/**", "/icons/**").permitAll() // 로그인 없이 접근 허용
    			.anyRequest().permitAll()              // 나머지도 임시로 허용
    		)
    		.formLogin(form -> form.disable())        // 기본 로그인 페이지 제거
	    	.logout(logout -> logout
	                .logoutUrl("/member/logout")
	                .logoutSuccessUrl("/member/login")
	                .permitAll()
	            );
    		
    	return http.build();
    }
	
	@Bean
	 public HttpFirewall allowDoubleSlashFirewall() {
	        StrictHttpFirewall firewall = new StrictHttpFirewall();
	        // double slash 허용
	        firewall.setAllowUrlEncodedDoubleSlash(true); // URL 인코딩된 // 허용 (%2F%2F)
//	        firewall.setAllowBackSlash(true); // (필요한 경우만)
//	        firewall.setAllowSemicolon(true); // (필요한 경우만)
//	        firewall.setAllowUrlEncodedSlash(true); // %2F 허용 (선택)
	        return firewall;
	    }
	 
	 @Bean
	    public WebSecurityCustomizer webSecurityCustomizer(HttpFirewall firewall) {
	        return web -> web.httpFirewall(firewall);
	    }

}
