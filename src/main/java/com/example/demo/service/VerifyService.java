package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.VerifyMapper;
import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;

import jakarta.mail.internet.MimeMessage;

@Service
public class VerifyService{
	
	@Autowired
	VerifyMapper verifyMapper;

	public AccountVerification findByCode(String code) {
		return verifyMapper.findByCode(code);
	}

	public int updateEmailVerified(String code) {
		return verifyMapper.updateEmailVerified(code);
	}

	public Member getTestMember() {
		return verifyMapper.getTestMember();
	}

	public void insertTestMember(Member member) {
		verifyMapper.insertTestMember(member);
	}

	public void insertVerification(AccountVerification verification) {
		verifyMapper.insertVerification(verification);
	}

	public int updateVerificationTable(String code) {
		return verifyMapper.updateVerificationTable(code);
	}


	public Member findIdMember(String emailId, String domain ) {
		
		return verifyMapper.findIdMember(emailId, domain);
	}

	
	
}
