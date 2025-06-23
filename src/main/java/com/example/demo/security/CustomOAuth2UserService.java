package com.example.demo.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberMapper memberMapper;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(request);

		String platform = request.getClientRegistration().getRegistrationId();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		
		String socialId = null;
		String name = null;
		String email = null;

		if ("google".equals(platform)) {
			socialId = attributes.get("sub").toString();
			name = (String) attributes.get("name");
			email = (String) attributes.get("email");
		} else if ("naver".equals(platform)) {
			attributes = (Map<String, Object>) attributes.get("response");
			socialId = attributes.get("id").toString();
			name = (String) attributes.get("name");
			email = (String) attributes.get("email");
		} else if ("kakao".equals(platform)) {
			socialId = attributes.get("id").toString();
			Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
		    name = (String) profile.get("nickname");
		    email = (String) kakaoAccount.get("email");
		} else {
			throw new IllegalArgumentException("Unknown platform: " + platform);
		}
		
		System.out.println("name:" + name);
		System.out.println("email:" + email);
		
		String emailId = "", emailDomain = "";
		if (email != null && email.contains("@")) {
			String[] emailParts = email.split("@");
			emailId = emailParts[0];
			emailDomain = emailParts[1];
		}

		Member member = findByEmail(emailId, emailDomain);
		if (member == null) {
			member = new Member();
			String shortUUID = UUID.randomUUID().toString().substring(0, 30);
			member.setId(shortUUID);
			member.setName(name);
//			member.setNickname(name);
			member.setSocialPlatform(platform);
			member.setSocialId(socialId);
			member.setEmailId(emailId);
			member.setEmailDomain(emailDomain);
			member.setPw("SOCIAL");
			member.setBirthDate("1900-01-01");
			member.setPhone("000-0000-0000");
			member.setGender("N");
			member.setEmailAd("N");
			member.setState(0);
			member.setEmailVerified("Y");
			memberMapper.socialInsert(member);
		}

		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		
		if(platform.equals("kakao"))
			return new DefaultOAuth2User(authorities, attributes, "id");
		else
			return new DefaultOAuth2User(authorities, attributes, "name");
	}

	public Member findByEmail(String socialId, String platform) {
		return memberMapper.findByEmail(socialId, platform);
	}
}
