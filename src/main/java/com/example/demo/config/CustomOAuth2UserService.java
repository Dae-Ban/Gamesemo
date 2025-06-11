package com.example.demo.config;

import java.util.Collections;
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

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final HttpSession session;
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String platform = request.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if ("naver".equals(platform)) {
            attributes = (Map<String, Object>) attributes.get("response");
        } else if ("kakao".equals(platform)) {
            attributes = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");
            attributes.put("nickname", profile.get("nickname"));
        }

        String socialId = attributes.get("id").toString();
        String name = (String) attributes.getOrDefault("name", attributes.get("nickname"));
        String email = (String) attributes.get("email");
        String emailId = "", emailDomain = "";

        if (email != null && email.contains("@")) {
            String[] emailParts = email.split("@");
            emailId = emailParts[0];
            emailDomain = emailParts[1];
        }

        // 기존 회원 조회 또는 신규 등록
        Member member = memberService.findBySocialIdAndPlatform(socialId, platform);
        if (member == null) {
            member = new Member();
            member.setId(UUID.randomUUID().toString());
            member.setName(name);
            member.setNickname(name);
            member.setSocialPlatform(platform);
            member.setSocialId(socialId);
            member.setEmailId(emailId);
            member.setEmailDomain(emailDomain);
            member.setState(1);
            member.setEmailVerified("Y");
            memberService.insertMember(member);
        }

        session.setAttribute("loginMember", member);
        return oAuth2User;
    }
}
