package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/member/update", "/member/login").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .logout(logout -> logout
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/member/login")
                .permitAll()
            );

        // OAuth2 설정 (명시적 방식)
//        http.apply(new OAuth2LoginConfigurer<HttpSecurity>()
//            .loginPage("/member/login") // 로그인 페이지 직접 지정
//            .defaultSuccessUrl("/oauth2/success", true)
//        );

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