package com.example.demo.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class OAuthController {

    private final HttpSession session;

    @GetMapping("/oauth2/success")
    public String oauth2Success(@AuthenticationPrincipal OAuth2User oAuth2User) {
    	String id;
    	if(oAuth2User.getAttribute("id") == null)
    		id = oAuth2User.getAttribute("sub").toString();
    	else
    		id = oAuth2User.getAttribute("id").toString();
        session.setAttribute("id", id);

        return "redirect:/";
    }
}
