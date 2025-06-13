package com.example.demo.controller;


import java.security.SecureRandom;
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
	
	@RequestMapping("/verifyNewMember")
	public String verifyNewMember(@RequestParam("code") String code, Model model) {
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
	
	@RequestMapping("/verifyFindId")
	public String verifyFindId(@RequestParam("code") String code, Model model ) {
		int result;
		AccountVerification verification = verifyService.findByCode(code);
		
		if (verification == null || "Y".equals(verification.getVerified())) {
			result = 0;
			model.addAttribute("result", result);
		} else if(verification.getExpiresAt().before(new Timestamp (System.currentTimeMillis()))) {
			result = -1;
		} else {
			result = verifyService.updateVerificationTable(code);
		}
		
		
		model.addAttribute("result", result);
		return "verify/verifyResult";
	}

	@RequestMapping("/test")
	public String testVerify() {
		//회원가입 인증 코드 생성
		String code = UUID.randomUUID().toString();
		String emailVerificationLink = code;
		
		//아이디 or 비밀번호 찾기 코드 생성
		SecureRandom secureRandom = new SecureRandom();
		int upperLimit = (int)Math.pow(10, 6); //두번째 파라미터는 자리수
		String sixDigitCode = Integer.toString(upperLimit);
		
				  			    // "MEMBER_JOIN"   //1 회원가입인증   
								// "FIND_PASSWORD" //그외 아이디 or 비밀번호 찾기
								// "FIND_ID"
		insertTest(sixDigitCode, "FIND_PASSWORD", 2);
		
		return "verify";
	}
	
	@RequestMapping("member/login")
	public String login() {
		return "member/login";
	}
	
	private void insertTest( String code, String type, int select) {
		if(select == 1) {
			Member member = new Member();
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			member.setId("tester54");
			member.setName("테스터54");
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
			verification.setType(type);
			Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
			verification.setExpiresAt(expiresAt);
			
			verifyService.insertTestMember(member);
			verifyService.insertVerification(verification);
			
			emailService.sendVerificationEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
		}else {
			
			AccountVerification verification = new AccountVerification();
			Member member = verifyService.findIdMember("2j1william","gmail.com"); //member email 값이 추후에 들어가야함
			
			verification.setId(member.getId());
			verification.setCode(code);
			verification.setType(type);
			Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10));
			verification.setExpiresAt(expiresAt);
			
			verifyService.insertTestMember(member);
			verifyService.insertVerification(verification);
			
			emailService.sendVerificationEmail(member.getEmailId() + "@" + member.getEmailDomain(), code);
			
		}
		
		
		
		

	}
}
