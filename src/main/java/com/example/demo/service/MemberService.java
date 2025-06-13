package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
@Transactional
public class MemberService{

    private final PasswordEncoder passwordEncoder;

	@Autowired
	private MemberMapper memberMapper;

    MemberService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	public boolean checkIdExists(String id) {
		return memberMapper.countById(id) > 0;
	}

	public boolean checkNicknameExists(String nickname) {
		 return memberMapper.countByNickname(nickname) > 0;
	}

	public boolean registerMember(Member member) {
	    int result = memberMapper.insert(member);  // Mapper에서 insert 수행
	    return result > 0;
	}

	public Member login(String id, String pw) {
		 Member member = memberMapper.findById(id);
		    if (member != null && passwordEncoder.matches(pw, member.getPw())) {
		        return member;
		    }
		    return null;
	}
	//회원정보수정
	public boolean updateMember(Member member) {
		 System.out.println("수정 요청된 정보: " + member);
		    int result = memberMapper.updateMember(member);
		    System.out.println("업데이트된 행 수: " + result);
		    
		return memberMapper.updateMember(member) > 0;
	}
	
	// ID로 회원 정보 조회 (세션 갱신용)
	public Member getMemberById(String id) {
	    return memberMapper.selectMemberById(id);
	}

	//비번수정
	public boolean updatePassword(Member member) {
		return memberMapper.updatePassword(member) > 0;
	}

	public boolean deleteMember(String id) {
		return memberMapper.deleteMember(id) > 0;
	}

	

}
