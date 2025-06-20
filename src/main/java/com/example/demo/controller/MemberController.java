
package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String loginform() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Member login,
                        @RequestParam(name="rememberMe", required = false)
    					String rememberMe,
                        Model model,
                        HttpSession session,
                        HttpServletResponse response) {
        System.out.println("입력 ID: " + login.getId());
        System.out.println("입력 PW: " + login.getPw());
        Member member = memberService.login(login);
        System.out.println("DB에서 찾은 Member: " + member);
        if (member != null) {
            session.setAttribute("id", member.getId());
            if(rememberMe != null) {
            	Cookie cookie = new Cookie("rememberId",member.getId());
            	cookie.setMaxAge(60 * 60 * 24 * 3);
            	cookie.setPath("/");
            	response.addCookie(cookie);	
            }
            String redirect = (String) session.getAttribute("redirectAfterLogin");
            if (redirect != null) {
                session.removeAttribute("redirectAfterLogin");
                return "redirect:" + redirect;
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
