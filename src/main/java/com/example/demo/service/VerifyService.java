package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.VerifyMapper;
import com.example.demo.model.Member;

import jakarta.mail.internet.MimeMessage;

@Service
public class VerifyService{
	
	@Autowired
	VerifyMapper verifyMapper;

	public Member findByToken(String token) {
		return verifyMapper.findByToken(token);
	}

	public int updateEmailVerified(String token) {
		return verifyMapper.updateEmailVerified(token);
	}

	public Member getTestMember() {
		return verifyMapper.getTestMember();
	}

	public void insertTestMember(Member member) {
		verifyMapper.insertTestMember(member);
	}

	
	
}
