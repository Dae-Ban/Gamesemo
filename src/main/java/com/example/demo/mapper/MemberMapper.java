package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
	//아이디 
	public int countById(@Param("id") String id);
	
	//닉네임 확인
	public int countByNickname(@Param("nickname") String nickname);
	
	//회원가입 저장
	public int insert(Member member);

	public Member findById(String id);

	public int updateMember(Member member);

	public Member selectMemberById(String id);

	public int updatePassword(Member member);

	public int deleteMember(String id);

}
