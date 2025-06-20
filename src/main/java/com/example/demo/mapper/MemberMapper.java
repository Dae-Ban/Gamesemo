package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
	// 아이디
	public int countById(@Param("id") String id);

	// 닉네임 확인
	public int countByNickname(@Param("nickname") String nickname);

	// 회원가입 저장
	public int insert(Member member);

	public Member findById(String id);

	public int updateMember(Member member);

	public Member selectMemberById(String id);

	public int updatePassword(Member member); // 일반 비번 변경

	public int deleteMember(String id);

	public Member selectByEmailForRegister(String email);

	public Member selectByEmailForFind(String email);
	
	public Member selectByIdAndEmail(@Param("id") String id, @Param("email") String email);

	public int updatePasswordForFind(Map<String, Object> paramMap); // 비번찾기 후 비번변경

	// 이메일인증 : 재원님꺼 추가함

	public Member findByEmail(@Param("email") String email);

	public int checkIdExists(String id);

	public Member findByEmailForRegister(String email);

	public boolean registerMember(Member member);

	public void insertMember(Member member);

	
	// 로그인 : 영교님꺼 추가
//
//	public Member modalLogin(@Param("id")String id, @Param("pw") String pw);
//
//	public Member findBySocialIdAndPlatform(String socialId, String platform);
//
//	public Member login(String id);

	//수정함 
	// 일반 로그인 (정상)
	public Member modalLogin(@Param("id") String id, @Param("pw") String pw);

	// 소셜 로그인 (❗수정 필요 → 아래처럼)
	public Member findBySocialIdAndPlatform(@Param("socialId") String socialId,
	                                        @Param("platform") String platform);

	// ID로 회원 조회 (정상)
	public Member login(String id);
}
