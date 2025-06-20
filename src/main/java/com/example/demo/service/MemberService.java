package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
public class MemberService {
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	MemberMapper memberMapper;
	
	

	public Member findByEmail(String email) {
		return memberMapper.findByEmail(email);
	}

	public boolean checkIdExists(String id) {
		return memberMapper.checkIdExists(id) > 0;
	}

	public boolean checkNicknameExists(String nickname) {
		return memberMapper.countByNickname(nickname) > 0;
	}

	public Object findByEmailForRegister(String email) {
		 return memberMapper.findByEmailForRegister(email);
	}

	public boolean registerMember(Member member) {
		return memberMapper.registerMember(member);
	}

	//영교님 꺼
	public Member login(Member login) {
		Member dbMember = memberMapper.login(login.getId());
		if(dbMember != null && passwordEncoder.matches(login.getPw(), dbMember.getPw())) {
			return dbMember;
		}
		return null;
	}
	
//	public Member modalLogin(member login) {
//		Member dbMember = memberMapper.modalLogin(id,pw);
//		if(dbMember != null && passwordEncoder.matches(pw, dbMember.getPw())) {
//			return dbMember;
//		}
//		return null;
//	}

	public Member findBySocialIdAndPlatform(String socialId, String platform) {
		return memberMapper.findBySocialIdAndPlatform(socialId, platform);
	}

	public Member findById(String id) {
		return memberMapper.findById(id);
	}
	
	public void insertMember(Member member) {
		memberMapper.insertMember(member);
	}
	

	
	
}