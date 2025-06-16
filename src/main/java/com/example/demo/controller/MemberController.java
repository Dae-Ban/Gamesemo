package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	// 회원가입 화면
	@GetMapping("/register")
	public String register() {
		return "member/register";
	}

	@PostMapping("/registerMember")
	public String register(@ModelAttribute Member member) {
		memberService.insertMember(member);
		return "redirect:/member/login";
	}

	@GetMapping("/find")
	public String find() {
		return "member/find";
	}

	// 아이디 중복 확인
	@ResponseBody
	@GetMapping("/check-id")
	public String checkId(@RequestParam("id") String id) {
		boolean exists = memberService.checkIdExists(id);
		return exists ? "duplicate" : "ok";
	}

	// 닉네임 중복 확인
	@ResponseBody
	@GetMapping("/check-nickname")
	public String checkNickname(@RequestParam("nickname") String nickname) {
		boolean exists = memberService.checkNicknameExists(nickname);
		return exists ? "duplicate" : "ok";
	}

	// 이메일 중복 확인
	@PostMapping("/checkEmailDuplicate")
	@ResponseBody
	public boolean checkEmailDuplicate(@RequestBody Map<String, String> data) {
		String email = data.get("email");
		return memberService.findByEmailForRegister(email) != null;
	}

}
