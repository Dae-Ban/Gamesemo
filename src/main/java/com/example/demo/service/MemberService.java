package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@Service
@Transactional
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberMapper memberMapper;
	private final EmailService emailService; // 이메일 인증 추가

	// 모든 필드를 생성자 주입으로 처리
	@Autowired
	public MemberService(PasswordEncoder passwordEncoder, MemberMapper memberMapper, EmailService emailService) {
		this.passwordEncoder = passwordEncoder;
		this.memberMapper = memberMapper;
		this.emailService = emailService;
	}

	public boolean checkIdExists(String id) {
		return memberMapper.countById(id) > 0;
	}

	public boolean checkNicknameExists(String nickname) {
		return memberMapper.countByNickname(nickname) > 0;
	}

	public boolean registerMember(Member member) {
		int result = memberMapper.insert(member); // Mapper에서 insert 수행
		return result > 0;
	}

	public Member login(String id, String pw) {
		Member member = memberMapper.findById(id);
		if (member != null && passwordEncoder.matches(pw, member.getPw())) {
			return member;
		}
		return null;
	}

	// 회원정보수정
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

	// 비번수정
	public boolean updatePassword(Member member) {
		return memberMapper.updatePassword(member) > 0;
	}

	public boolean deleteMember(String id) {
		return memberMapper.deleteMember(id) > 0;
	}

	public Member findByEmailForRegister(String email) {
	    return memberMapper.selectByEmailForRegister(email);
	}

	public Member findByEmailForFind(String email) {
	    return memberMapper.selectByEmailForFind(email);
	}

	//회원가입 인증 기존 재원님 EmailService에 있는 걸 그대로 이용
	public void sendAuthCode(String email, String code) {
	    emailService.sendVerificationEmail(email, code); 
	}
	//아이디 찾기 인증 (6자리 숫자)
	public void sendIdAuthCode(String email, String code) {
	    emailService.sendFindIdEmail(email, code);
	}

	// 비번찾기에서 아이디이메일일치 
	public Member findByIdAndEmail(String id, String email) {
		 return memberMapper.selectByIdAndEmail(id, email);
	}

	public Member findById(String id) {
		 return memberMapper.findById(id);
	}

	public boolean updatePasswordForFind(Map<String, Object> paramMap) {
		return memberMapper.updatePasswordForFind(paramMap) > 0;
	}

}
