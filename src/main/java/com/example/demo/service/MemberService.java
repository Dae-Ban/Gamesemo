package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper memberMapper;
	
	public void insertMember(Member member) {
		memberMapper.insertMember(member);
	}

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


	
	
}
