package com.example.demo.controller;


import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Member;
import com.example.demo.service.EmailService;
import com.example.demo.service.VerifyService;

@Controller
public class VerifyController {

	@Autowired
	private VerifyService verifyService;

	@Autowired
	private EmailService emailService;

	@RequestMapping("/verify")
	public String verifyEmail(@RequestParam("token") String token, Model model) {
		int result;
		
		Member member = verifyService.findByToken(token);

		if (member == null || "y".equalsIgnoreCase(member.getEmailVerified())) {
			result = 0;
			model.addAttribute("result", result);
			return "verify/verifyResult";
		}

		result = verifyService.updateEmailVerified(token);
		model.addAttribute("result", result);
		return "verify/verifyResult";
	}

	@RequestMapping("/testVerify")
	public String testVerify() {
		String token = UUID.randomUUID().toString();
		String emailVerificationLink = token;

		Member member = new Member();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		member.setId("tester22");
		member.setName("테스터22");
		member.setNickname("TEST");
		member.setPw("1234");
		member.setBirthDate("19900101");
		member.setEmailId("2j1william");
		member.setEmailDomain("gmail.com");
		member.setPhone("12345");
		member.setGender("남");
		member.setJoinDate(ts);
		member.setState(2);
		member.setEmailAd("y");
		member.setEmailVerified("n");
		member.setToken(token);

		verifyService.insertTestMember(member);

//		String link = "http://localhost/verify?token=" + token;
		emailService.sendVerificationEmail(member.getEmailId() + "@" + member.getEmailDomain(), emailVerificationLink);

		return "verify";
	}
	
	@RequestMapping("member/login")
	public String login() {
		return "member/login";
	}
}
