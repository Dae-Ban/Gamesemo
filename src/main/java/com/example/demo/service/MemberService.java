
package com.example.demo.service;

import java.util.List;
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


//	
//	영교님과 중복 주석처리
//	public Member login(String id, String pw) {
//		Member member = memberMapper.findById(id);
//		if (member != null && passwordEncoder.matches(pw, member.getPw())) {
//			return member;
//		}
//		return null;
//	}
	
	public boolean updateMember(Member member) {
		return memberMapper.updateMember(member) > 0;
	}

	public boolean updatePassword(Member member) {
		return memberMapper.updatePassword(member) > 0;
	}

	public boolean deleteMember(String id) {
		return memberMapper.deleteMember(id) > 0;
	}

	public Member getMemberById(String id) {
		return memberMapper.selectMemberById(id);
	}

	public void sendAuthCode(String email, String code) {
		emailService.sendVerificationEmail(email, code);
	}

	public void sendIdAuthCode(String email, String code) {
		emailService.sendFindIdEmail(email, code);
	}

	public Member findByEmailForFind(String email) {
		return memberMapper.selectByEmailForFind(email);
	}

	public Member findByIdAndEmail(String id, String email) {
		return memberMapper.selectByIdAndEmail(id, email);
	}

	public boolean updatePasswordForFind(Map<String, Object> paramMap) {
		return memberMapper.updatePasswordForFind(paramMap) > 0;
	}

	// 지선, 재원 중복 맞춤
	public Member findByEmail(String email) {
		return memberMapper.findByEmail(email);
	}
	
	public boolean checkIdExists(String id) {
		return memberMapper.countById(id) > 0;
	}
	
	public boolean checkNicknameExists(String nickname) {
		return memberMapper.countByNickname(nickname) > 0;
	}
	
	public Member findByEmailForRegister(String email) {
		return memberMapper.selectByEmailForRegister(email);
	}
	
	public boolean registerMember(Member member) {
		return memberMapper.insert(member) > 0;
	}
	
	
	//영교님 꺼 
	public Member login(Member login) {
		Member dbMember = memberMapper.login(login.getId());
		if(dbMember != null && passwordEncoder.matches(login.getPw(), dbMember.getPw())) {
			return dbMember;
		}
		return null;
	}

	public Member findBySocialIdAndPlatform(String socialId, String platform) {
		return memberMapper.findBySocialIdAndPlatform(socialId, platform);
	}
	
	public Member findById(String id) {
		return memberMapper.findById(id);
	}
	
	public List<Member> selectAllEmailSubscribers() {

	      return memberMapper.selectAllEmailSubscribers();
	   }
	
}
