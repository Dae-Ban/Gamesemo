package com.example.demo.config;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final HttpSession session;
	
	@Override
	 public OAuth2User loadUser(OAuth2UserRequest request) {
		OAuth2User oAuth2User = super.loadUser(request);
		
		return oAuth2User;
	}
}
