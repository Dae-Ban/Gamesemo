package com.example.demo.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.AccountVerification;
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
	public String verifyEmail(@RequestParam("code") String code, Model model) {
		int result;
		AccountVerification verification = verifyService.findByCode(code);
		
		if (verification == null || "Y".equals(verification.getVerified())) {
			result = 0;
			model.addAttribute("result", result);
		} else if(verification.getExpiresAt().before(new Timestamp (System.currentTimeMillis()))) {
			result = -1;
		} else {
			verifyService.updateVerificationTable(code);
			result = verifyService.updateEmailVerified(code);
		}
		
		model.addAttribute("result", result);
		return "verify/verifyResult";
	}

	@RequestMapping("/testVerify")
	public String testVerify() {
		String code = UUID.randomUUID().toString();
		String emailVerificationLink = code;
		
		Member member = new Member();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		member.setId("tester37");
		member.setName("테스터37");
		member.setNickname("TEST");
		member.setPw("1234");
		member.setBirthDate("19900101");
		member.setEmailId("2j1william");
		member.setEmailDomain("gmail.com");
		member.setPhone("12345");
		member.setGender("남");
		member.setJoinDate(ts);
		member.setState(2);
		member.setEmailAd("Y");
		member.setEmailVerified("N");
		
		AccountVerification verification = new AccountVerification();
		
		verification.setId(member.getId());
		verification.setCode(code);
		verification.setType("MEMBER_JOIN");
		Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
		verification.setExpiresAt(expiresAt);
		
		verifyService.insertTestMember(member);
		verifyService.insertVerification(verification);
//		verfifyService.insertVerificationForJoin();

//		String link = "http://localhost/verify?token=" + token;
		
//		  = verifyService.findByToken(code);

		emailService.sendVerificationEmail(member.getEmailId() + "@" + member.getEmailDomain(), emailVerificationLink);

		return "verify";
	}
	
	@RequestMapping("member/login")
	public String login() {
		return "member/login";
	}
}
