package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.mapper.VerifyMapper;
import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;

@Service
public class VerifyService{
	
	@Autowired
	VerifyMapper verifyMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	public AccountVerification findByCode(String code, String type) {
		return verifyMapper.findByCode(code, type);
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

	public int updateVerificationTable(String code, String type) {
		return verifyMapper.updateVerificationTable(code, type);
	}


	public Member findIdMember(String email) {
		
		return verifyMapper.findIdMember(email);
	}

	public boolean isEmailExist(String email) {
		return verifyMapper.isEmailExist(email);
	}

	public AccountVerification findByEmailAndType(String email, String type) {
		return verifyMapper.findByEmailAndType(email, type);
	}

	public Member findIdMember(String email, String domain) {
		return verifyMapper.findIdMember(email, domain);
	}

	public void updateMemberTable(String id) {
		verifyMapper.updateMemberTable(id);
	}

	
	
}
