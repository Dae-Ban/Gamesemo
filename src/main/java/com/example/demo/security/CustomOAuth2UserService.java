package com.example.demo.security;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String platform = request.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String socialId = null;
        String name = null;
        String email = null;

        if ("google".equals(platform)) {
            // ✅ 구글은 id 대신 sub!
            socialId = attributes.get("sub").toString();
            name = (String) attributes.get("name");
            email = (String) attributes.get("email");
        } else if ("naver".equals(platform)) {
            attributes = (Map<String, Object>) attributes.get("response");
            socialId = attributes.get("id").toString();
            name = (String) attributes.get("name");
            email = (String) attributes.get("email");
        } else if ("kakao".equals(platform)) {
            attributes = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");
            socialId = attributes.get("id").toString();
            name = (String) profile.get("nickname");
            email = (String) attributes.get("email");
        } else {
            throw new IllegalArgumentException("Unknown platform: " + platform);
        }

        String emailId = "", emailDomain = "";
        if (email != null && email.contains("@")) {
            String[] emailParts = email.split("@");
            emailId = emailParts[0];
            emailDomain = emailParts[1];
        }

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

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // ✅ key는 socialId로 할 수도 있고, 그냥 name으로도 할 수 있음.
        return new DefaultOAuth2User(authorities, attributes, "name");
    }
}
