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
	private final EmailService emailService;

	@Autowired
	public MemberService(PasswordEncoder passwordEncoder, MemberMapper memberMapper, EmailService emailService) {
		this.passwordEncoder = passwordEncoder;
		this.memberMapper = memberMapper;
		this.emailService = emailService;
	}

	// 아이디 중복 확인
	public boolean checkIdExists(String id) {
		return memberMapper.countById(id) > 0;
	}

	// 닉네임 중복 확인
	public boolean checkNicknameExists(String nickname) {
		return memberMapper.countByNickname(nickname) > 0;
	}

	// 회원 등록
	public boolean registerMember(Member member) {
		return memberMapper.insert(member) > 0;
	}

	// 로그인
	public Member login(String id, String pw) {
		Member member = memberMapper.findById(id);
		if (member != null && passwordEncoder.matches(pw, member.getPw())) {
			return member;
		}
		return null;
	}

	// 회원정보 수정
	public boolean updateMember(Member member) {
		return memberMapper.updateMember(member) > 0;
	}

	// 비밀번호 변경
	public boolean updatePassword(Member member) {
		return memberMapper.updatePassword(member) > 0;
	}

	// 회원 탈퇴
	public boolean deleteMember(String id) {
		return memberMapper.deleteMember(id) > 0;
	}

	// ID로 회원 정보 조회 (세션 갱신용)
	public Member getMemberById(String id) {
		return memberMapper.selectMemberById(id);
	}

	// 이메일 인증 (회원가입용)
	public void sendAuthCode(String email, String code) {
		emailService.sendVerificationEmail(email, code);
	}

	// 이메일 인증 (아이디 찾기용)
	public void sendIdAuthCode(String email, String code) {
		emailService.sendFindIdEmail(email, code);
	}

	// 이메일로 회원 조회 (아이디 찾기)
	public Member findByEmailForFind(String email) {
		return memberMapper.selectByEmailForFind(email);
	}

	// 이메일로 회원 조회 (회원가입 시 중복 확인)
	public Member findByEmailForRegister(String email) {
		return memberMapper.selectByEmailForRegister(email);
	}

	// 이메일로 회원 정보 조회 (공통)
	public Member findByEmail(String email) {
		return memberMapper.findByEmail(email);
	}

	// 아이디 + 이메일로 회원 찾기 (비밀번호 찾기용)
	public Member findByIdAndEmail(String id, String email) {
		return memberMapper.selectByIdAndEmail(id, email);
	}

	// 비밀번호 재설정 (비밀번호 찾기용)
	public boolean updatePasswordForFind(Map<String, Object> paramMap) {
		return memberMapper.updatePasswordForFind(paramMap) > 0;
	}

	// 회원 직접 삽입 (관리자/테스트용)
	public void insertMember(Member member) {
		memberMapper.insertMember(member);
	}

	public Member findById(String id) {
		return memberMapper.findById(id);
	}

	// 로그인 : 영교님
	public Member login(Member login) {
//		return memberMapper.login(login);
		Member dbMember = memberMapper.login(login.getId());
		if(dbMember != null && passwordEncoder.matches(login.getPw(), dbMember.getPw())) {
			return dbMember;
		}
		return null;
	}

	public Member findBySocialIdAndPlatform(String socialId, String platform) {
		return memberMapper.findBySocialIdAndPlatform(socialId, platform);
	}

	
}
