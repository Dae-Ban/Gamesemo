package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper{
	//아이디 
	public int countById(@Param("id") String id);
	
	//닉네임 확인
	public int countByNickname(@Param("nickname") String nickname);
	
	//회원가입 저장
	public int insert(Member member);

	public Member findById(String id);

	public int updateMember(Member member);

	public Member selectMemberById(String id);

	public int updatePassword(Member member); //일반 비번 변경

	public int deleteMember(String id);

	public Member selectByEmailForRegister(String email);
	
	public Member selectByEmailForFind(String email);

	public Member selectByIdAndEmail(@Param("id") String id,  @Param("email") String email);

	public int updatePasswordForFind(Map<String, Object> paramMap); //비번찾기후 비번변경

}
