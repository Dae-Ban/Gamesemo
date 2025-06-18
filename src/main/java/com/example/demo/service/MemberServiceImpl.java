package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
    public Member login(Member login) {
		Member dbMember = memberMapper.findById(login.getId());
		if(dbMember != null && passwordEncoder.matches(login.getPw(), dbMember.getPw())) {
			return dbMember;
		}
		return null;
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
