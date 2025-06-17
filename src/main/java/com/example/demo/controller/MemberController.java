package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public String loginform() {
        return "member/login";
    }

    @PostMapping("")
    public String login(@RequestParam String id,
                        @RequestParam String pw,
                        @RequestParam(required = false)
    					String rememberMe,
                        Model model,
                        HttpSession session,
                        HttpServletResponse response) {

        Member member = memberService.login(id, pw);

        if (member != null) {
            session.setAttribute("loginMember", member);
            if(rememberMe != null) {
            	Cookie cookie = new Cookie("rememberId",id);
            	cookie.setMaxAge(60 * 60 * 24 * 3);
            	cookie.setPath("/");
            	response.addCookie(cookie);
            	
            }
            return "redirect:/"; // 로그인 성공 시 메인 페이지로 이동
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "member/login"; // 로그인 실패 시 로그인 화면으로
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
    	session.invalidate();
        Cookie cookie = new Cookie("rememberId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    	return "redirect:/login";
    }
}
