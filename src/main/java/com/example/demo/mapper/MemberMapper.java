package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
	

	public List<Member> selectAllEmailSubscribers();

	public void insertMember(Member member);

	public Member findByEmail(@Param("email") String email);

	public int checkIdExists(String id);

	public int countByNickname(String nickname);

	public Member findByEmailForRegister(String email);

	public boolean registerMember(Member member);

	public Member modalLogin(@Param("id")String id, @Param("pw") String pw);

	public Member findBySocialIdAndPlatform(String socialId, String platform);

	public Member login(String id);

	public Member findById(@Param("id")String id);
}
