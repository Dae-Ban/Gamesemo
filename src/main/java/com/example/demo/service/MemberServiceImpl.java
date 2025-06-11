package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
    public Member login(String id, String pw) {
		return memberMapper.findByIdAndPw(id, pw);
    }

	@Override
	public Member findBySocialIdAndPlatform(String socialId, String platform) {
        return memberMapper.findBySocialIdAndPlatform(socialId, platform);
	}

	@Override
	public void insertMember(Member member) {
        memberMapper.insert(member);
		
	}
}
