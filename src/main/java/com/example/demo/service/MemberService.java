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
	
	
}
