package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {
	//회원가입
	@GetMapping("/register")
	public String register() {
			return "member/register";
	}
	
	//회원수정
	@GetMapping("/update")
	public String update() {
		return "member/update";
	}

	//비번변경
	@GetMapping("/changePassword")
	public String changePassword() {
		return "member/changePassword";
	}
	
	//아이디 비번찾기
	@GetMapping("/find")
	public String find() {
		return "member/find";
	}
	
	//회원탈퇴
	@GetMapping("/delete")
	public String delete() {
		return "member/delete";
	}
}
